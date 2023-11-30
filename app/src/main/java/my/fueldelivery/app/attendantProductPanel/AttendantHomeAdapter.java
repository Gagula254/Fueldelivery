package my.fueldelivery.app.attendantProductPanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import my.fueldelivery.app.R;
import my.fueldelivery.app.UpdateProductModel;

public class AttendantHomeAdapter extends RecyclerView.Adapter<AttendantHomeAdapter.ViewHolder>{

    private Context mcont;
    private List<UpdateProductModel> updateProductModelList;

    public AttendantHomeAdapter(Context context , List<UpdateProductModel>updateProductModelList){
        this.updateProductModelList = updateProductModelList;
        this.mcont = context;
    }

    @NonNull
    @Override
    public AttendantHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcont).inflate(R.layout.chefmenu_update_delete,parent,false);
        return new AttendantHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendantHomeAdapter.ViewHolder holder, int position) {

        final UpdateProductModel updateProductModel = updateProductModelList.get(position);
        holder.products.setText(updateProductModel.getProducts());
        updateProductModel.getRandomUID();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcont,UpdateDelete_Product.class);
                intent.putExtra("updatedeleteproduct",updateProductModel.getRandomUID());
                mcont.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return updateProductModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView products;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            products = itemView.findViewById(R.id.product_name);
        }
    }
}
