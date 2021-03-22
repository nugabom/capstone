package com.example.myapplication.bookTime

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.Store
import com.example.myapplication.WaitFragment
/*
import com.example.myapplication.bookMenu.DataMenuToPay
import com.example.myapplication.bookMenu.MenuData
import com.example.myapplication.bookMenu.MenuFragment
import com.example.myapplication.bookTable.TableData
import com.example.myapplication.bookTable.TableFragment
 */
import com.example.myapplication.dataclass.StoreInfo
import com.example.myapplication.mainPage.SikdangList
//import com.example.myapplication.payPage.PayPage
import com.example.myapplication.sikdangChoicePage.SikdangChoiceCatAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


//아예 시간표는 프래그먼트로 넣을까?

class BookTime: AppCompatActivity() {
    val fragmentManager = supportFragmentManager

    lateinit var bookData :BookData
    //lateinit var menuData: MenuData
    //lateinit var tableData: TableData

    lateinit var sikdangName :TextView
    lateinit var sikdangIV : ImageView
    var fragmentPage = 0

    //var tableFragment = TableFragment()
    //var timeFragment = TimeFragment()
    //var menuFragment = MenuFragment()

    //TableFloorFragment에서 받아와 MenuFragment로 전달하는 데이터
    //몇페이지인가와 그 페이지의 테이블 정보를 가져와서 리스트에 추가한다
    var isTableInfoInit : Boolean = false
    var tableNumAL = ArrayList<ArrayList<Int>>()//각 층의 각 테이블에 몇명 예약했는지를 저장하는 리스트

    //각 층의 테이블정보 담은 String 각 층의 정보 다 쓰면 뒤에 n 붙은다 12302n100104n4123n 식으로 붙음
    var tableNumARString = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booktime)
        Log.d("확인 BookTime", "onCreate")
        //식당 id 불러와서 BookTimeData에 데이터 set

        sikdangName = findViewById(R.id.bookTime_sikdangName)
        fragmentManager.beginTransaction()
                .replace(R.id.bookFragment, WaitFragment()).commit()

        var sikdangId = intent.getExtras()!!.getString("sikdangId")
        sikdangIV = findViewById(R.id.bookTime_sikdangImage)
        Log.d("sikdangID", sikdangId.toString())
        var reference = FirebaseDatabase.getInstance().getReference("Restaurants")
                .child("닭고기")
                .child(sikdangId!!)
                .child("info")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val store_info = snapshot.getValue(StoreInfo::class.java)
                        sikdangName.setText(store_info!!.store_name)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

        BookDataBuilder(sikdangId, this).build()
        //식당 이름 se
        //bookData = BookData(sikdangId!!)
        //menuData = MenuData(sikdangId!!)

        //sikdangIV.setBackgroundResource(bookData.getSikdangImage())

        //식당 이미지 set


        //시간표 리사이클러뷰에 바인드
        /*
        var bookTimeRV:RecyclerView = findViewById(R.id.bookTimeRV)
        var bookTimeRVAdapter = BookTimeRVAdapter(this, bookTimeData)

        bookTimeRV.adapter = bookTimeRVAdapter

        var sikdangChice_catLineLM = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        bookTimeRV.layoutManager=sikdangChice_catLineLM
        bookTimeRV.setHasFixedSize(true)*/

        //showBookTime(bookTimeData)

        //replaceTimeFragment(timeFragment)
        /*
        var bundle:Bundle = Bundle()
        bundle.putSerializable("bookData", bookData)
        timeFragment.setArguments(bundle)
        fragmentPage=1

        fragmentTransaction.replace(R.id.bookFragment, timeFragment).commit()*/

        //fragmentTransaction.replace(R.id.bookFragment, timeFragment)

        //fragmentTransaction.add(R.id.bookFragment, timeFragment)
        //fragmentTransaction.commit()

    }

    fun callback(fragment : Fragment) {
        fragmentManager.beginTransaction().replace(R.id.bookFragment, fragment).commit()
    }
}
/*

    public fun replaceTimeFragment(timeFragment: Fragment) {
        fragmentPage=1
        var bundle:Bundle = Bundle()
        bundle.putSerializable("bookData", bookData)
        timeFragment.setArguments(bundle)
        //val fragmentManager = supportFragmentManager
        val fragmentTransaction2 = fragmentManager.beginTransaction()

        //fragmentTransaction2.replace(R.id.bookFragment, timeFragment).commit()
        fragmentTransaction2.replace(R.id.bookFragment, timeFragment)
        //fragmentTransaction2.addToBackStack(null)
        fragmentTransaction2.commit()
    }

    //테이블 프래그먼트 호출하는 함수
    //백으로 돌아오면서 이 함수 호출하면 프래그먼트 초기값이 이미 설정되어있어야 한다

    public fun replaceTableFragment() {
        var tableFragment = TableFragment()
        fragmentPage = 2
        var bundle:Bundle = Bundle()
        bundle.putSerializable("bookData", bookData)
        tableData= TableData(bookData.getSikdangId(), bookData.getBookTime())
        bundle.putSerializable("tableData", tableData)
        tableFragment.setArguments(bundle)
        val fragmentTransaction3 = fragmentManager.beginTransaction()

        //fragmentTransaction3.replace(R.id.bookFragment, tableFragment).commit()
        fragmentTransaction3.replace(R.id.bookFragment, tableFragment)
        fragmentTransaction3.addToBackStack(null)
        fragmentTransaction3.commit()
    }




    public fun replaceMenuFragment() {
        var menuFragment = MenuFragment()
        fragmentPage = 3
        var bundle:Bundle = Bundle()
        bundle.putSerializable("bookData", bookData)
        bundle.putSerializable("menuData", menuData)
        bundle.putSerializable("tableData", tableData)
        bundle.putString("tableNumARString", tableNumARString)
        //Log.d("확인 replaceMenuFragment ArrayList.Tostring ", tableNumAL.toString())

        bundle.putParcelableArrayList("tableNumAR", tableNumAL as ArrayList<out Parcelable?>?)
        //bundle.putSerializableExtras()
        //bundle.putString("tableNumAR", tableNumAR.toString())
        menuFragment.setArguments(bundle)
        val fragmentTransaction4 = fragmentManager.beginTransaction()

        //fragmentTransaction4.replace(R.id.bookFragment, menuFragment).commit()
        fragmentTransaction4.replace(R.id.bookFragment, menuFragment)
        fragmentTransaction4.addToBackStack(null)
        fragmentTransaction4.commit()

    }



    //그 뭐냐 그 뒤로가기 버튼 눌으면 전 프래그먼트로 돌아가게 해야함
    //전 프래그먼트로 돌아갈 시 데이터 없어짐 - 데이터 이어지도록 해야 함 - 좀 나중으로 미룸?

    /*
    override fun onBackPressed() {
        if (fragmentPage == 1){
            Log.d("확인 onBackPressed", fragmentPage.toString())
            super.onBackPressed()
        }
        else if(fragmentPage == 2){
            Log.d("확인 onBackPressed", fragmentPage.toString())
            replaceTimeFragment(timeFragment)
        }
        else if(fragmentPage == 3){
            Log.d("확인 onBackPressed", fragmentPage.toString())
            logTableNumAl()
            replaceTableFragment(true)
        }
        else {
            Log.d("확인 onBackPressed", fragmentPage.toString())
            super.onBackPressed()
        }
    }*/

    //TableFloorFragment에서 호출
    //페이지와 그 페이지의 각 테이블에 몇명 앉는가의 정보를 받아온다
    //매번 데이터를 받아와햐하는데 그러려변 미리 ArrayList가 초기화되어있어야 한다
    //TableFragment 에서 총 몇층인지, 각 층의 테이블이 몇개인지 매개변수로 전달받아 초기화
    public fun tableInfoInit(floorNum:Int, tableNum:ArrayList<Int>){//이건 아마 TableFragment에서 호출해야할듯
        var i = 0
        if (isTableInfoInit == false) {
            while (i < floorNum) {
                var j = 0
                var tempAR = ArrayList<Int>()
                while (j < tableNum[i]) {
                    tempAR.add(0)
                    tableNumARString+=0.toString()
                    j++
                }
                tableNumARString+="n"
                tableNumAL.add(tempAR)
                i++
            }
            //Log.d("확인 BookTime.TableNumAR", "초기화")
            //Log.d("확인 BookTime.TableNumARStraing", tableNumARString)
            logTableNumAl()
        }
        isTableInfoInit = true//초기화 끝나고 다시 안바뀌도록 한다
    }

    public fun setTableInfoToString(){
        //Log.d("확인 BookTime.setTableInfoToString() 문자열 변환 확인", "시작")
        var i = 0
        var tempString = ""
        while (i < tableNumAL.size) {
            var j = 0
            var tempAR = ArrayList<Int>()
            while (j < tableNumAL[i].size) {
                //tableNumARString[k] = tableNumAR[i][j]
                tempString+= tableNumAL[i][j].toString()
                j++
            }
            tempString+="n"
            i++
        }
        tableNumARString = tempString
        //Log.d("확인 BookTime.setTableInfoToString() 문자열 변환 확인", tableNumARString)
        logTableNumAl()


    }

    public fun setTableInfo(tablePage_:Int, tableNumAR_:ArrayList<Int>){
        //tablePage.add(tablePage_)
        tableNumAL[tablePage_] = tableNumAR_
        var i = 0
        setTableInfoToString()
        logTableNumAl()
    }

    private fun logTableNumAl(){
        var i = 0
        var logString=""
        while(i<tableNumAL.size){
            var j = 0
            while (j<tableNumAL[i].size){
                logString+=tableNumAL[i][j].toString()
                j++
            }
            i++
        }
        //Log.d("확인 BookTime.TableNumAL", logString)

    }


    public fun setTableDataOn(tableData_:TableData){
        tableData=tableData_
    }


    public fun callPayPage(price:Int, dataMenuToPay: DataMenuToPay){
        Log.d("확인 BookTime.callPayPage()", "시작")
        dataMenuToPay.setOnSikdangInfo(bookData.getSikdangId(), bookData.getSikdangName())
        val intent= Intent(this, PayPage::class.java)
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("price", price)
        intent.putExtra("dataMenuToPay", dataMenuToPay)
        startActivity(intent)
        Log.d("확인 BookTime.callPayPage()", "끝")

        //itemView.getContext().startActivity(intent);
    }
/*
    fun getStoreInfo(catory : String, sikdangId : String): StoreInfo {
        var result : StoreInfo? = null
        var key : String? = null
        var reference = FirebaseDatabase.getInstance().getReference("Restaurants")
                .child(catory)
                .child(sikdangId)
                .child("info")
        reference.addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(store_info: DataSnapshot) {
                        key = store_info.key!!
                        result = store_info.getValue(StoreInfo::class.java)
                        Log.d("resultddfsfdsf", result.toString())
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("getStoreInfo","Read : Restaurants/${catory}/${sikdangId}/${key}: Error")
                    }
                })
        if(result == null) {
            Log.d("getStoreInfo","Route : Restaurants/${catory}/${sikdangId}/${key}: Error")
        }
        return result!!
    }
*/
/*
    public fun showBookTime(bookTimeData:BookTimeData){
        val transaction = manager.beginTransaction()
        val fragment = BookTimeFragment(bookTimeData)
        transaction.add(R.id.bookfragment, fragment)
        transaction.commit()

    }*/
}
 */