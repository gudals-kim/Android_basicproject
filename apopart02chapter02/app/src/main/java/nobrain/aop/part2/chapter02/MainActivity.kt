package nobrain.aop.part2.chapter02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val clearButton: Button by lazy{
        findViewById<Button>(R.id.clearButton)

    }

    private val addButton: Button by lazy{//객체생성 추가버튼
        findViewById<Button>(R.id.addButton)
    }
    private val runButton: Button by lazy{//객체생성 실행버튼
        findViewById<Button>(R.id.runButton)
    }
    private val numberPicker: NumberPicker by lazy{//객체생성 넘버피커
        findViewById<NumberPicker>(R.id.numberPicker)
    }


    private val numberTextViewList: List<TextView> by lazy{//객체 생성 보이는번호들
        listOf<TextView>(
            findViewById<TextView>(R.id.textView1),
            findViewById<TextView>(R.id.textView2),
            findViewById<TextView>(R.id.textView3),
            findViewById<TextView>(R.id.textView4),
            findViewById<TextView>(R.id.textView5),
            findViewById<TextView>(R.id.textView6)
        )

    }
    //번호를 추가할수 없는 상태 예외처리
    private var didRun = false
    //중복 불가 예외처리
    private val pickNumberSet = hashSetOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
        initClearButton()

    }

    private fun initRunButton(){
        //실행버튼을 누르면 랜덤번호를 가져오는 함
        runButton.setOnClickListener {
            val list = getRandomNumber()

            didRun = true

            list.forEachIndexed{ index, number ->
                val textView = numberTextViewList[index]

                textView.text = number.toString()
                textView.isVisible = true

                setNumberBackground(number, textView)
            }

        }
    }

    private fun getRandomNumber(): List<Int>{
        //6개의 랜덤번호가 담긴 리스트를 만드는 함수
        val numberList = mutableListOf<Int>()
            .apply {
                for (i in 1..45) {
                    if (pickNumberSet.contains(i)){//이미고른 번호 제외
                        continue
                    }
                    //선택번호 제외하고 추출함
                    this.add(i)
                }
            }
        numberList.shuffle()
        //내가고른 숫자와 자동추출 숫자를 합침
        val newList = pickNumberSet.toList() + numberList.subList(0,6 - pickNumberSet.size)//SubList(시작위치,끝위치) 리스트를 만들어줌

        return newList.sorted()
    }

    private fun initAddButton(){
        //add 버튼을 눌렀을때
        addButton.setOnClickListener {
            if (didRun){
                //add 버튼을 누르기전에 자동번호을 눌러서 번호가 이미 있는우 경
                Toast.makeText(this,"초기화 후에 시도해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(pickNumberSet.size >= 5){
                //선택 번호가 5개 이상일  경우
                Toast.makeText(this,"번호는 5개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pickNumberSet.contains(numberPicker.value)){
                //이미 선택한 번호가 들어갔을 경우 Ture일 경우
                Toast.makeText(this,"이미 선택한 번호입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //예외 처리 끝
            val textView = numberTextViewList[pickNumberSet.size]
            //pickNumberset의 크기가 보이는 textviewlist의 배열 순서

            textView.isVisible = true
            textView.text = numberPicker.value.toString()

            setNumberBackground(numberPicker.value, textView)


            //넘버피커의 값을 돌려줌
           pickNumberSet.add(numberPicker.value)

        }
    }
    private fun setNumberBackground(number:Int, textView: TextView){
        when(number){
            in 1..10 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_yello)
            in 11..20 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_blue)
            in 21..30 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_red)
            in 31..40 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_gray)
            else -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_green)
        }
    }

    private fun initClearButton(){
        //초기화 버튼 기능
        clearButton.setOnClickListener {
            pickNumberSet.clear( )//고른번호 초기화
            numberTextViewList.forEach{
                it.isVisible = false
            }
        }
        didRun = false
    }

}