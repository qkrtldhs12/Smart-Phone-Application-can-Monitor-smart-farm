package com.example.morta.mytestapi

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle
import android.util.Log
import com.example.morta.mytestapi.R.id.testView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val builder: Retrofit.Builder = Retrofit.Builder()
                .baseUrl("https://api.neople.co.kr/") //api 서버 URL ex)깃헙 api
                .addConverterFactory(GsonConverterFactory.create())

        val retrofit: Retrofit = builder.build()

        val client = retrofit.create(Api_Client::class.java)

        val call = client.query("경호의작은좋", "MosNoYNhUKGHypdW3A6TAt8BeuJwiRcQ") //api 호출시 보낼 파라미터, 이 경우 깃헙 사용자 a1216843

        //비동기 실행을 위해 사용하는 enqueue 메소드는 callback을 기다리고, callback은 서버로부터 response를 받을 때마다 실행한다.
        call.enqueue(object : Callback<NeopleRepo> {
            override fun onFailure(call: Call<NeopleRepo>, t: Throwable) {
                Log.d("TAG", "실패: $t")
            }

            override fun onResponse(call: Call<NeopleRepo>, response: Response<NeopleRepo>) {
                //response를 저장할 List<GitHunRepo> 타입의 변수 repos를 선언 후 response 할당
                //response에서 텍스트를 추출해 저장할 변수
                //추출한 텍스트를 텍스트뷰에 띄움
                Log.d("TAG", "성공 : ${response.raw()}")
                Log.d("TAG", "성공 : ${response.body()}")
                val repos:NeopleRepo? = response.body()
                var reposStr: String = ""
                repos?.rows?.forEach { it ->
                    Log.d("TAG", "${it.characterName}")
                    reposStr += "Charater_Name: ${it.characterName}\n"
                    Log.d("TAG", "${it.characterId}")
                    reposStr += "Character_Id: ${it.characterId}\n"
                    Log.d("TAG", "${it.serverId}")
                    reposStr += "Server_Id: ${it.serverId}\n"
                    Log.d("TAG", "${it.level}")
                    reposStr += "Level: ${it.level}\n"
                    Log.d("TAG", "${it.jobGrowId}")
                    reposStr += "Job_GrowId: ${it.jobGrowId}\n"
                    Log.d("TAG", "${it.jobId}")
                    reposStr += "Job_Id: ${it.jobId}\n"
                    Log.d("TAG", "${it.jobGrowName}")
                    reposStr += "Job_GrowName: ${it.jobGrowName}\n"
                    Log.d("TAG", "${it.jobName}")
                    reposStr += "Job_Name: ${it.jobName}\n"
                }
                testView.text = reposStr
            }
        })
    }

}
