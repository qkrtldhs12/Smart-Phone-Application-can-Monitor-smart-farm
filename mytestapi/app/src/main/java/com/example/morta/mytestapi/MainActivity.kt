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
                .baseUrl("https://api.github.com/") //api 서버 URL ex)깃헙 api
                .addConverterFactory(GsonConverterFactory.create())

        val retrofit: Retrofit = builder.build()

        val client = retrofit.create(GithubClient::class.java)

        val call = client.reposForUser("a1216843") //api 호출시 보낼 파라미터, 이 경우 깃헙 사용자 a1216843

        //비동기 실행을 위해 사용하는 enqueue 메소드는 callback을 기다리고, callback은 서버로부터 response를 받을 때마다 실행한다.
        call.enqueue(object : Callback<List<GitHubRepo>> {
            override fun onFailure(call: Call<List<GitHubRepo>>, t: Throwable) {
                Log.d("TAG", "실패: $t")
            }

            override fun onResponse(call: Call<List<GitHubRepo>>, response: Response<List<GitHubRepo>>) {
                //response를 저장할 List<GitHunRepo> 타입의 변수 repos를 선언 후 response 할당
                val repos:List<GitHubRepo>? = response.body()
                //response에서 텍스트를 추출해 저장할 변수
                var reposStr = ""
                //repos의 각 요소 it에 대해서 순서대로 reposStr에 더해줌
                repos?.forEach { it ->
                    reposStr += "$it\n"
                }
                //추출한 텍스트를 텍스트뷰에 띄움
                testView.text = reposStr
            }
        })
    }

}
