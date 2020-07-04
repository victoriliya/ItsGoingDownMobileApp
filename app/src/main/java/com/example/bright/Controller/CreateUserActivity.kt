package com.example.bright.Controller

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.bright.R
import com.example.bright.Services.AuthServices
import com.example.bright.Services.UserDataService
import com.example.bright.Utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_create_user.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        createSpinner.visibility = View.INVISIBLE
    }

    fun generateUserAvatar(view: View){

        val random = java.util.Random()
        val color=  random.nextInt(2)
        val avatar = random.nextInt(28)

        if (color == 0){
            userAvatar = "light$avatar"
        }else{
            userAvatar = "dark$avatar"
        }

        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)

        createAvatarImage.setImageResource(resourceId)

    }

    fun generateColorClicked(view: View){

        val random = java.util.Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)

        createAvatarImage.setBackgroundColor(Color.rgb(r,g,b))

        val savedR = r.toDouble() / 255
        val savedG = g.toDouble() / 255
        val savedB = b.toDouble() / 255

        avatarColor = "[$savedR, $savedG, $savedB, 1]"

        println(avatarColor)

    }

    fun createUserClicked(view: View){
            enableSpinner(true)
            val userName = createUserNameText.text.toString()
            val email = createUserEmailText.text.toString()
            val password = createPasswordText.text.toString()

            if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() ){

                AuthServices.registerUser( email, password){ registerSuccess ->
                    if (registerSuccess){
                        AuthServices.loginUser( email, password){ loginSuccess ->
                            if (loginSuccess){
                                AuthServices.createUser( userName, email,  userAvatar, avatarColor){ createSuccess ->
                                    if (createSuccess){
                                        val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                                        LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                                        println("User created successfully")
                                        enableSpinner(false)
                                        finish()
                                    }else{
                                        errorToast()
                                    }
                                }
                            }else{
                                errorToast()
                            }

                        }
                    }else{
                        errorToast()
                    }
                }
            }else{
                Toast.makeText(this, "Make sure username, email and password are filled",
                    Toast.LENGTH_LONG).show()
                enableSpinner(false)
            }
        }


    fun errorToast(){
        Toast.makeText(this, "Something went wrong, please try again.",
            Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    fun  enableSpinner(enable: Boolean){

        if (enable){
            createSpinner.visibility = View.VISIBLE
        }else{
            createSpinner.visibility = View.INVISIBLE
        }
        createUserBtn.isEnabled=  !enable
        createAvatarImage.isEnabled = !enable
        backgroundColorBtn.isEnabled = !enable
    }

}
