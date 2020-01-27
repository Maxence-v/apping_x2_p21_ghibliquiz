package fr.epita.android.ghibliquizzpartielandroid.Adaptaters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import fr.epita.android.ghibliquizzpartielandroid.Models.PeopleObject
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.epita.android.ghibliquizzpartielandroid.R

const val genderMaleUrl = "https://banner2.cleanpng.com/20180331/wyq/kisspng-gender-symbol-female-sign-male-5ac0133669efd6.4100095915225372704339.jpg"
const val genderFemaleUrl = "https://banner2.cleanpng.com/20180607/cxk/kisspng-smbolo-de-venus-planet-symbols-gender-symbol-aphrodite-stakes-5b18bfa9de4e24.5209213615283485859106.jpg"

class AnswerListAdapter(
    private val context : Context,
    private val data: ArrayList<PeopleObject>,
    private val itemOnClickListener: View.OnClickListener
) : RecyclerView.Adapter<AnswerListAdapter.ViewHolder>() {

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView = itemView.findViewById(R.id.name)
        val genderView: ImageView = itemView.findViewById(R.id.gender)
        val ageView: TextView = itemView.findViewById(R.id.age)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowView = LayoutInflater
            .from(context)
            .inflate(R.layout.activity_main_answer_row, parent, false)

        rowView.setOnClickListener(itemOnClickListener)

        return ViewHolder(rowView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]

        holder.nameView.text = currentItem.name
        holder.ageView.text = currentItem.age

        if (currentItem.gender == "Male") {
            Glide
                .with(context)
                .load(genderMaleUrl)
                .error(AppCompatResources.getDrawable(context, R.drawable.error))
                .into(holder.genderView)
        } else {
            Glide
                .with(context)
                .load(genderFemaleUrl)
                .error(AppCompatResources.getDrawable(context, R.drawable.error))
                .into(holder.genderView)
        }



        holder.itemView.tag = currentItem.name
    }
}