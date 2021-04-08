package com.example.morta.mytestapi

import com.google.gson.annotations.SerializedName

data class NeopleRepo (
        var rows: List<result>
)

data class result(
        var characterName: String = "",
        var characterId: String = "",
        var serverId: String ="",
        var level: Int = 0,
        var jobId: String = "",
        var jobGrowId: String = "",
        var jobName: String = "",
        var jobGrowName: String = ""
)