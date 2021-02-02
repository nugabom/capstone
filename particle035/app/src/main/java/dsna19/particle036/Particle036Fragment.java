package dsna19.particle036;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Particle036Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Particle036Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Particle036Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private MinPQ<Event>        mPQ;
    private Particle[]          mParticle;
    private double              mTime = 0.0;
    private double              mHz = 0.5;
    private int                 mLimit = 1000000;

    private Draw                mDraw = null;
    private boolean             mAnimate;
    public Particle036Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Particle007Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Particle036Fragment newInstance(String param1, String param2) {
        Particle036Fragment fragment = new Particle036Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mParticle = new Particle[50];
        for (int i = 0; i < mParticle.length; i++)
            mParticle[i] = new Particle(Color.rgb(
                    RandGen.uniform(255),
                    RandGen.uniform(255),
                    RandGen.uniform(255)
            ));
        mPQ = new MinPQ<Event>();
        for (int i = 0; i < mParticle.length; i++)
            predict(mParticle[i], mLimit);
        mPQ.insert(new Event(0, null, null));
        mAnimate = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDraw = new Draw(getContext());
        container.addView(mDraw);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void simulate(){
        while (!mPQ.isEmpty()){
            Event e = mPQ.delMin();
            if (!e.isValid()) continue;
            Particle a = e.mmA;
            Particle b = e.mmB;
            for (int i = 0; i < mParticle.length; i++)
                mParticle[i].move(e.mmTime - mTime);
            mTime = e.mmTime;
            if (a != null && b != null) a.bounceOff(b);
            else if (a != null && b == null) a.bounceOffVerticalWall();
            else if (a == null && b != null) b.bounceOffHorizontalWall();
            else if (a == null && b == null)
                break;
            predict(a, mLimit);
            predict(b, mLimit);
        }
        if (mTime < mLimit)
            mPQ.insert(new Event(mTime + 1.0/mHz, null, null));
    }
    private void predict (Particle a, double limit){
        if (a == null) return;
        for (int i = 0; i < mParticle.length; i++) {
            double dt = a.timeToHit(mParticle[i]);
            if (mTime + dt < limit)
                mPQ.insert(new Event(mTime + dt, a, mParticle[i]));
        }
        double dtX = a.timeToHitVerticalWall();
        double dtY = a.timeToHitHorizontalWall();
        if (mTime + dtX <= limit) mPQ.insert(new Event(mTime + dtX, a, null));
        if (mTime + dtY <= limit) mPQ.insert(new Event(mTime + dtY, null, a));
    }

    public void toggleAnimate (){
        mAnimate ^= true;
        if (mAnimate) mDraw.invalidate();
    }
    protected class Draw extends View {
        private Paint mmPaint;
        private Bitmap mmBack;
        private Canvas mmOffScreen;
        private int     mmWidth;
        private int     mmHeight;

        public Draw(Context context) {
            super(context);
            mmPaint = new Paint();
            mmPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        }
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            mmWidth = w;
            mmHeight = h;
            mmBack = Bitmap.createBitmap(mmWidth, mmHeight, Bitmap.Config.ARGB_8888);
            mmOffScreen = new Canvas(mmBack);
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            simulate();
            mmOffScreen.drawColor(Color.WHITE);
            // draw particles
            for (int i = 0; i < mParticle.length; i++) {
                mmPaint.setColor(mParticle[i].getColor());
                mmOffScreen.drawCircle((int)(mParticle[i].getRx() * mmWidth),
                        (int)(mParticle[i].getRy()* mmHeight),
                        (int)(mParticle[i].getRadius() *1000),
                        mmPaint);
            }
            canvas.drawBitmap(mmBack, 0, 0, null);
            if (mAnimate) invalidate();
        }
    }
    private static class Event implements Comparable<Event> {
        private final double    mmTime;                // time that event is scheduled to occur
        private final Particle  mmA, mmB;             // particles involved in event, possibly null
        private final int       mmCountA, mmCountB;   // collision counts at event creation

        // create a new event to occur at time t involving a and b
        public Event(double t, Particle a, Particle b) {
            this.mmTime = t;
            this.mmA    = a;
            this.mmB    = b;
            if (a != null) mmCountA = a.count();
            else           mmCountA = -1;
            if (b != null) mmCountB = b.count();
            else           mmCountB = -1;
        }

        // compare times when two events will occur
        public int compareTo(Event that) {
            return Double.compare(this.mmTime, that.mmTime);
        }

        // has any collision occurred between when event was created and now?
        public boolean isValid() {
            if (mmA != null && mmA.count() != mmCountA) return false;
            if (mmB != null && mmB.count() != mmCountB) return false;
            return true;
        }
    }
}
