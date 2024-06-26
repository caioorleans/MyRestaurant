package com.orleanscaio.myrestaurant.recyclerviewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.orleanscaio.myrestaurant.R
import com.orleanscaio.myrestaurant.dish.Dish
import java.io.IOException

class AdapterDishMenu(context:Context,
                      dishes:ArrayList<Dish>,
                      recyclerInterface: DishMenuAdapterInterface)
    : RecyclerView.Adapter<AdapterDishMenu.MyViewHolder>() {

    private var recyclerViewInterface: DishMenuAdapterInterface

    private var context: Context
    private var dishes: ArrayList<Dish>
    init {
        this.context = context
        this.dishes = dishes
        this.recyclerViewInterface = recyclerInterface
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //Aqui o layout é inflado
        val INFLATER: LayoutInflater = LayoutInflater.from(context)
        val VIEW:View = INFLATER.inflate(R.layout.card_dish_menu, parent, false)

        return MyViewHolder(VIEW)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //Associa as views criadas ao valor de cada uma
        holder.nameTextView.text = dishes[position].name
        holder.priceTextView.text = buildString {
            append("R$ ")
            append("%.2f".format(dishes[position].cost))
        }

        val HOUR:Int = dishes[position].timeToPrepare/60
        val MINUTES:Int = dishes[position].timeToPrepare%60
        holder.preparationTimeTextView.text = buildString {
            append(HOUR)
            append(" h ")
            append(MINUTES)
            append(" m")
        }

        try {
            val IMAGE= context.assets.open(dishes[position].imageUri).readBytes()
            Glide.with(context).load(IMAGE)
                .placeholder(R.drawable.baseline_restaurant_menu_24).centerCrop().into(holder.imageView)
        }catch (exception:IOException){
            Glide.with(context).load(R.drawable.baseline_sentiment_very_dissatisfied_24)
                .centerCrop().into(holder.imageView)
        }

        holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION)
                recyclerViewInterface.onItemClick(dishes[position])
        }

    }

    override fun getItemCount(): Int {
        //Conta quantos itens nós temos
        return dishes.size
    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imageView: ImageView
        var nameTextView: TextView
        var priceTextView: TextView
        var preparationTimeTextView: TextView
        init {
            imageView = itemView.findViewById(R.id.image_dish)
            nameTextView = itemView.findViewById(R.id.text_dish_name)
            priceTextView = itemView.findViewById(R.id.text_dish_cost)
            preparationTimeTextView = itemView.findViewById(R.id.text_dish_preparation_time)
        }

    }
}