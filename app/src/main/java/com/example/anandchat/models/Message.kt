package com.example.anandchat.models

import java.security.PrivateKey
import javax.crypto.SecretKey

data class Message(val text:String="", val sender:String="", val sentAt:String="", val createdAt:Long=0L,val em:String="" )
