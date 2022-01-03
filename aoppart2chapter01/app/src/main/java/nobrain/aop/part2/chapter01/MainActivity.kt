package nobrain.aop.part2.chapter01

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //입력폼 변수
        val heightEditText: EditText = findViewById(R.id.heightEditText)
        val weightEditText = findViewById<EditText>(R.id.weightEditText)
        //결과버튼 변수
        val resultButton = findViewById<Button>(R.id.resultButton)

        //resultButton 클릭시 일어나는 행동들 정의
        resultButton.setOnClickListener {
            Log.d("mainActivity","ResultButton 이 클릭되었습니다.")

            if(heightEditText.text.isEmpty()||weightEditText.text.isEmpty()){
                //isEmpty 함수는 text 가 비어있을 경우 ture 비어있지 않을 경우 false
                Toast.makeText(this,"빈 값이 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener //setOnclick 함수를 나
                //Toast 오류메시지 뛰움
                //인자로 context, text(뛰울 메시지), 뛰울 시간
            }
            //이 아래는 빈값이 나올 수 없음

            //이렇게만 하면 오류가 날수 잇음 toStr/toInt 를 해야하는데 값을 넣지 않고 버튼을 넣으면 바꿀게없으니 if 문을 만들어주자
            val height:Int = heightEditText.text.toString().toInt()//height 를 int 로 받기위한 방식
            val weight:Int = weightEditText.text.toString().toInt()

            Log.d("MainActivity","height")

            //결과화면으로 연결
            val intent = Intent(this, ResultActivity::class.java)

            //인덴트에 변수 담아서 보내는 법
            intent.putExtra("height",height)
            intent.putExtra("weight",weight)
            startActivity(intent)
        }
    }
}