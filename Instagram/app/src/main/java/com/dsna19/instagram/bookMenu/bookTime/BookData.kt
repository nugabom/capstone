package com.example.myapplication.bookTime

import android.util.Log
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.Serializable
import java.util.concurrent.Semaphore

class BookData (var timeArrayList: ArrayList<String>, var isFull : ArrayList<Boolean>, var sikdangId : String) {
    init {
        Log.d("BookData", "${timeArrayList}")
        Log.d("BookData", "${isFull}")
    }
}

//프래그먼트간에 intent로 안되니 Bundle로 넘김
//여기서 Serializable
/*
class BookData(sikdangId_: String):Serializable {
    private var sikdangName: String = ""
    private var sikdangImage: Int = 0
    private val sikdangId = sikdangId_
    private var timeArrayList: ArrayList<String> = arrayListOf() //예약 시간을 arrayList로
    private  var isFull: ArrayList<Boolean> = arrayListOf()// 각 예약시간대에 예약이 차있는가
    private var done = Semaphore(0)

    init {
        var tasks = arrayListOf(
                setData(sikdangId_)
        )

        Tasks.whenAll(tasks)
                .continueWith(RollbackIfFailure())
    }

    private var floor: Int = 2//층수
    private var bookTime: String = ""


    private fun dummy() {
        Thread.sleep(1000)
    }

    public fun setName(sikdangName_: String) {
        sikdangName = sikdangName_
    }


    public fun getSikdangId(): String {
        return sikdangId
    }

    public fun getSikdangImage(): Int {
        return sikdangImage
    }

    public fun getSikdangName(): String {
        return sikdangName
    }

    public fun getTimeArrayList(): ArrayList<String> {
        return timeArrayList
    }

    public fun getIsFull(): ArrayList<Boolean> {
        return isFull
    }

    public fun getFloor(): Int {
        return floor
    }

    public fun getBookTime(): String {
        return bookTime
    }


    public fun setBookTime(time: String) {
        bookTime = time
    }


    //여기세 데이터베이스 접속해서
    //지금 들어가있는건 임시 데이터
    //아래있는것들만 데이터베이스에서 호출


    data class IsFull(val current: Int? = null, val max: Int? = null)

    inner class initializer(val id: String) : Thread() {
        override fun run() {
            Log.d("시발", "시발놈아")
            setData(id)
        }
    }

    fun setData(sikdangId: String) : Task<String> {
        var tcs = TaskCompletionSource<String>()
        var reference = FirebaseDatabase.getInstance().getReference("Tables")
                .child(sikdangId)
        Log.d("setData", "동기화 시작")

        val bookTree = hashMapOf<String, Boolean>()

        reference.child("TableInfo").child("nFloor").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val num = snapshot.getValue(Long::class.java)
                Log.d("num", num.toString())
                if (num == null) {
                    Log.d("TableInfo", "Null Error")
                }
                for (i in 0 until num!!) {
                    Log.d("floor", "floor_${i + 1}")
                    reference.child("Booked").child("floor_${i + 1}").addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(floor: DataSnapshot) {
                            for (time in floor.children) {
                                val isFulled = time.getValue(BookData.IsFull::class.java)
                                Log.d("timetable", "${time.key} : ${isFulled}")
                                val key = time.key.toString()
                                if (isFulled!!.current == 0) {
                                    bookTree.put(key, true)
                                } else if ((isFulled.current != 0) && bookTree.containsKey(key)) {
                                    bookTree[key] = false
                                } else {
                                    bookTree.put(key, false)
                                }
                            }

                            Log.d("bookTree", bookTree.toString())
                            timeArrayList = ArrayList(bookTree.keys)
                            for (is_full in timeArrayList) {
                                isFull.add(bookTree[is_full]!!)
                            }
                            Log.d("timeArrayList", "${timeArrayList.toString()}")
                            Log.d("isFull", "${isFull.toString()}")
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
                }
                done.release()
                Log.d("래치 done", "끝")
                Log.d("key", "${snapshot.key}")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("TableInfo_onCancelled", error.message)
            }
        })
        Log.d("그립고", "그립다")
        return tcs.task
    }
}

internal class RollbackIfFailure : Continuation<Void?, Task<Void?>?> {
    @Throws(Exception::class)
    override fun then(task: Task<Void?>): Task<Void?> {
        val tcs = TaskCompletionSource<Void?>()
        if (task.isSuccessful) {
            tcs.setResult(null)
        } else {
            // Rollback everything
        }
        return tcs.task
    }
}
 */