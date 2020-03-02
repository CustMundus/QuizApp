package QuizApp.com

import android.os.AsyncTask
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val myPlant: Plant = Plant("","","","","","",0,0)
    }
    fun firstClicked() = Unit
    fun secondClicked() = Unit
    fun thirdClicked() = Unit
    fun fourthClicked() = Unit

    inner class downloadingPlantText: AsyncTask<String, Int, List<Plant>>(){
        override fun doInBackground(vararg params: String?): List<Plant> {
            //background process
        }

        override fun onPostExecute(result: List<Plant>?) {
            super.onPostExecute(result)

            //Can access user interface thread. Not background
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
