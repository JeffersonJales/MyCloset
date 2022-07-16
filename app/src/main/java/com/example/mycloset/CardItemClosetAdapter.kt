package adapters

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mycloset.ItemCloset
import com.example.mycloset.R
import com.example.mycloset.VisualizaritemActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class CardItemClosetAdapter(private val itemList : ArrayList<String>, val activity : Activity) : RecyclerView.Adapter<CardItemClosetAdapter.ViewHolder>() {

    var fStorage = FirebaseStorage.getInstance().getReference()
    var fDatabase = FirebaseDatabase.getInstance().getReference()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.scroll_view_item, parent, false)
        return ViewHolder(view)
    }

    // Ligando o Recycler view a um View Holder
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var itemClosetId : String = ""
        val cardInCell: LinearLayout = ItemView.findViewById(R.id.CardInCell)
        val ivCardImage: ImageView = ItemView.findViewById(R.id.ivCardImage)
        val tvCardNome: TextView = ItemView.findViewById(R.id.tvCardNome)
        val tvCardDescricao: TextView = ItemView.findViewById(R.id.tvCardDescricao)
    }

    // faz o bind de uma ViewHolder a um Objeto da Lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = itemList[position]
        holder.itemClosetId = item

        val databaseListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {

                var itemCloset = snapshot.getValue<ItemCloset>()

                holder.tvCardNome.text = itemCloset?.nome
                holder.tvCardDescricao.text = itemCloset?.descricao

                fStorage.
                    child("foto_item_closet").
                    child(itemCloset?.foto.toString()).
                    getBytes(1024*1024).addOnSuccessListener{
                        val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                        holder.ivCardImage.setImageBitmap(bmp)
                }
            }
        }

        fDatabase.
            child("item_closet").
            child(item).
            addValueEventListener(databaseListener)


        holder.cardInCell.setOnClickListener{
            val intent = Intent(activity, VisualizaritemActivity::class.java).apply{}
            intent.putExtra("itemClosetId", holder.itemClosetId)
            activity.startActivity(intent)
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return itemList.size
    }
}
