package dsna19.particle036;

public class Particle {
    private static final double INFINITY = Double.POSITIVE_INFINITY;

    private double  mRx, mRy;     // position
    private double  mVx, mVy;     // velocity
    private double  mRadius;       // radius
    private double  mMass;         // mass
    private int     mColor;        // color
    private int     mCount;        // number of collisions so far

    public Particle(int color){
        mRx     = RandGen.uniform(0.0, 1.0);
        mRy     = RandGen.uniform(0.0, 1.0);
        mVx     = RandGen.uniform(-0.009, 0.009);
        mVy     = RandGen.uniform(-0.009, 0.009);
        mRadius = 0.02;
        mMass   = 0.4;
        mColor = color;
    }

    public void move(double dt) {
        mRx += mVx * dt;
        mRy += mVy * dt;
    }
    public double getRx(){
        return mRx;
    }
    public double getRy(){
        return mRy;
    }
    public double getRadius(){
        return mRadius;
    }
    public int getColor(){
        return this.mColor;
    }

    public int count() {
        return mCount;
    }

    public double timeToHit(Particle that) {
        if (this == that) return INFINITY;
        double dx  = that.mRx - this.mRx;
        double dy  = that.mRy - this.mRy;
        double dvx = that.mVx - this.mVx;
        double dvy = that.mVy - this.mVy;
        double dvdr = dx * dvx + dy * dvy;
        if (dvdr > 0) return INFINITY;
        double dvdv = dvx * dvx + dvy * dvy;
        double drdr = dx*dx + dy*dy;
        double sigma = this.mRadius + that.mRadius;
        double d = (dvdr * dvdr) - dvdv * (drdr - sigma * sigma);
        if (d < 0) return INFINITY;
        return -(dvdr + Math.sqrt(d)) / dvdv;
    }

    public double timeToHitVerticalWall() {
        if      (mVx > 0) return (1.0 - mRx - mRadius) / mVx;
        else if (mVx < 0) return (mRadius - mRx) / mVx;
        else              return INFINITY;
    }

    public double timeToHitHorizontalWall() {
        if      (mVy > 0) return (1.0 - mRy - mRadius) / mVy;
        else if (mVy < 0) return (mRadius - mRy) / mVy;
        else              return INFINITY;
    }

    public void bounceOff(Particle that) {
        double dx  = that.mRx - this.mRx;
        double dy  = that.mRy - this.mRy;
        double dvx = that.mVx - this.mVx;
        double dvy = that.mVy - this.mVy;
        double dvdr = dx*dvx + dy*dvy;                  // dv dot dr
        double dist = this.mRadius + that.mRadius;    // distance between particle centers at collison

        // magnitude of normal force
        double magnitude
                = 2 * this.mMass * that.mMass * dvdr / ((this.mMass + that.mMass) * dist);

        // normal force, and in x and y directions
        double fx = magnitude * dx / dist;
        double fy = magnitude * dy / dist;

        // update velocities according to normal force
        this.mVx += fx / this.mMass;
        this.mVy += fy / this.mMass;
        that.mVx -= fx / that.mMass;
        that.mVy -= fy / that.mMass;

        // update collision counts
        this.mCount++;
        that.mCount++;
    }

    public void bounceOffVerticalWall() {
        mVx = -mVx;
        mCount++;
    }

    public void bounceOffHorizontalWall() {
        mVy = -mVy;
        mCount++;
    }
}