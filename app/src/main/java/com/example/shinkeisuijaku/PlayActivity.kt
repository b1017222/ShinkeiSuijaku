package com.example.shinkeisuijaku

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.shinkeisuijaku.R.drawable.*
import com.example.shinkeisuijaku.R.raw.*
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random


class PlayActivity : AppCompatActivity() {

    //トランプ(ボタン)の数、トランプ５２枚、音ファイル
    private val numberOfTrumps =20
    private val effectSounds = listOf(start,push,wrong,ok)//音ファイル
    private val cloverCards =listOf(clover__1,clover__2,clover__3,clover__4,clover__5,clover__6,clover__7,clover__8,clover__9,clover__10,clover__11,clover__12,clover__13)//トランプの画像
    private val diaCards =listOf(dia__1,dia__2,dia__3,dia__4,dia__5,dia__6,dia__7,dia__8,dia__9,dia__10,dia__11,dia__12,dia__13)
    private val hartCards =listOf(heart__1,heart__2,heart__3,heart__4,heart__5,heart__6,heart__7,heart__8,heart__9,heart__10,heart__11,heart__12,heart__13)
    private val spadeCards =listOf(spade__1,spade__2,spade__3,spade__4,spade__5,spade__6,spade__7,spade__8,spade__9,spade__10,spade__11,spade__12,spade__13)
    lateinit var imageViews: List<View>


    //変数var
    //private var randomCards =mutableListOf<Int>()//ランダムに生成したカード2組合計２０枚を格納する
    private var tagOfButtons =mutableListOf<Button>()//19個まで
    private var music = mutableListOf<Int>()//各音楽を再生するオブジェクトを格納する配列
    private var gameStartDate = Date()//ゲームはじまる時間を格納する
    private var clearTime :Double = 0.0
    private var hashTable =mutableListOf<Int>(0,0,0,0,0,0,0,0,0,0,0,0,0)
    private var selectedButton: Button? = null
    private var selectedView: View?= null
    private val clearTimeKey = "CLEAR_TIME"
    private var count=0
    lateinit var soundPool: SoundPool



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        imageViews = listOf(card_btn1,card_btn2,card_btn3,card_btn4,card_btn5,card_btn6,card_btn7,
            card_btn8,card_btn9,card_btn10,card_btn11,card_btn12,card_btn13,card_btn14,card_btn15,
            card_btn16,card_btn17,card_btn18,card_btn19,card_btn20)//setContentView読み込んだ後にリストの宣言
        prepareMusic()
        extractionCards()
    }
    override fun onResume(){
        super.onResume()//onCreateとonResumeで呼ばれる関数の中にボタンが押された時に動くsetOnClickListener

        //再開するたびに呼ばれる関数を記入
    }

    private fun prepareMusic(){//
        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .build()
        effectSounds.forEach{
            val id = soundPool.load(this, it, 1);//MediaPlayerのクラス内でなければ宣言できない
            music.add(id)
        }
    }



    //カードをランダムに抽出する
    private fun extractionCards(){

        while(tagOfButtons.count()<numberOfTrumps) {//ボタンの数を満たすまで
            val randomNum = Random.nextInt(0, 12)

            if (hashTable[randomNum] < 2) {
                hashTable[randomNum] += 1
                val button1 = Button(this)//this=PlayActivity
                val button2 = Button(this)

//                button1.tag = randomNum + 1
//                button2.tag = randomNum + 1

                //ただのボタンをリストに入れる
                tagOfButtons.add(button1)
                tagOfButtons.add(button2)
            }
        }




        var imageHandlePrefix = listOf<Int>()//空の配列の用意
        var index = 0



            hashTable.forEachIndexed{ i, value ->   //hashTableの要素数を見ていく、it番目の要素(２or４分)のタグをボタンが２０個入ったリストtagOfButtons.tagに登録する
                //タグの数分のカードの柄を生成している
                val handleNumber = mutableListOf<Int>(1,2,3,4)//柄の数をシャッフルする用
                handleNumber.shuffle()//

                if(hashTable[i]==1){//hashTableを見ていき、配列に値が入っていたらその要素数が１個ならランダムでカードを二枚決める

                    when(handleNumber[0]) {

                        1 -> imageHandlePrefix = cloverCards
                        2 -> imageHandlePrefix = diaCards
                        3 -> imageHandlePrefix = hartCards
                        4 -> imageHandlePrefix = spadeCards
                    }
                    //ボタンの配列のタグを作る
                    tagOfButtons[index].tag = i + 1
                    tagOfButtons[index].setBackgroundResource(imageHandlePrefix[tagOfButtons[index].tag as Int -1])
                    index += 1

                    when(handleNumber[1]){
                         1 -> imageHandlePrefix = cloverCards
                         2 -> imageHandlePrefix = diaCards
                         3 -> imageHandlePrefix = hartCards
                         4 -> imageHandlePrefix = spadeCards

                    }
                    tagOfButtons[index].tag = i + 1
                    tagOfButtons[index].setBackgroundResource(imageHandlePrefix[tagOfButtons[index].tag as Int -1])
                    index += 1
                }

                if(hashTable[i]==2){//hashTableを見ていき、配列が２なら全部の柄に決める

                    tagOfButtons[index].tag = i+1
                    tagOfButtons[index].setBackgroundResource(cloverCards[tagOfButtons[index].tag as Int -1])
                    index += 1

                    tagOfButtons[index].tag = i+1
                    tagOfButtons[index].setBackgroundResource(diaCards[tagOfButtons[index].tag as Int -1])
                    index += 1

                    tagOfButtons[index].tag = i+1
                    tagOfButtons[index].setBackgroundResource(hartCards[tagOfButtons[index].tag as Int -1])
                    index += 1


                    tagOfButtons[index].tag = i+1
                    tagOfButtons[index].setBackgroundResource(diaCards[tagOfButtons[index].tag as Int -1])
                    index += 1
                }


            }




        //ボタンの配列シャッフル
        tagOfButtons.forEachIndexed{ i ,_-> //0(インデックス)を無限ループ？
            val newIndex = Random.nextInt(0, 19)
            if(i != newIndex){
                Collections.swap(tagOfButtons, i,newIndex)
            }
        }
        tagOfButtons.forEachIndexed{index, value->

            var layoutParams = imageViews[index].layoutParams
            root.addView(tagOfButtons[index], layoutParams)
            tagOfButtons[index].visibility = View.INVISIBLE//無くなる

        }
        imageViews.forEachIndexed{index, imageView ->//indexとバリュー
            imageView.setOnClickListener {   //カードボタンはimageViewsの要素
               buttonPressed(tagOfButtons[index],imageView)
            }
        }
    }









    // ボタンが押された時の処理
    private fun buttonPressed(button: Button,view :View){
        //startButtonAnimation(button)
        button.visibility = View.VISIBLE



        if( selectedButton == null && selectedView==null) {//ここでこの二つの変数を定義しないのは初期化を防ぐため
            //1回目
            selectedButton = button//buttonPressedのタグの番号
            selectedView =view
            soundPool.play(music[1], 1.0f, 1.0f, 1, 0, 1.0f)


        } else {
            //2回目
            if (selectedButton?.tag == button.tag){
                //正解
                count += 1
                Log.v("GameActivity","正解")
                soundPool.play(music[3], 1.0f, 1.0f, 1, 0, 1.0f)


                GlobalScope.launch {
                    //どっちも消すボタンだけじゃなく、Viewごと消す
                    delay(1000L)
                    selectedView?.visibility = View.INVISIBLE
                    view.visibility = View.INVISIBLE
                    selectedButton?.visibility = View.INVISIBLE//無くなる、１回目に入って来たボタン
                    button.visibility = View.INVISIBLE//無くなる、２回目に入って来たボタン
                    selectedButton = null
                    selectedView = null

                    if(count==10){
                        //時間を渡す
                        val intent = Intent(application, FinishActivity::class.java)
                        Log.v("GameActivity","終了")
                        //intent.putExtra(clearTimeKey, clearTime)
                        startActivity(intent)
                    }
                }


            } else {
                //不正解
                soundPool.play(music[2], 1.0f, 1.0f, 1, 0, 1.0f)

                GlobalScope.launch {
                    delay(1000L)
                    selectedButton?.visibility = View.INVISIBLE//無くなる、１回目に入って来たボタン
                    button.visibility = View.INVISIBLE//無くなる、２回目に入って来たボタン

                    selectedButton = null
                    selectedView = null
                }
            }


        }
    }


}


