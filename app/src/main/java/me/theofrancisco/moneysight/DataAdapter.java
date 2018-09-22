package me.theofrancisco.moneysight;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DataAdapter extends ArrayAdapter<Data> {
    private int color;
    private Context context;

    DataAdapter(Context context, List<Data> datas, int color) {
        super(context, 0, datas);
        this.context = context;
        this.color = color;
    }

    @Override
    public View getView(@NonNull int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        listItemView = LayoutInflater.from(getContext()).inflate(
                R.layout.list_item, parent, false);


        listItemView.setBackgroundColor(color);
        // Get the {@link AndroidFlavor} object located at this position in the list
        final Data data = getItem(position);
        TextView tvTitle = listItemView.findViewById(R.id.tvTitle);
        tvTitle.setText(data.getWebTitle());

        TextView tvSectionName = listItemView.findViewById(R.id.tvSectionName);
        tvSectionName.setText(data.getSectionName());

        TextView tvDate = listItemView.findViewById(R.id.tvDate);
        tvDate.setText(data.getWebPublicationDate());

        ImageView ivThumbnail = listItemView.findViewById(R.id.ivThumbnail);
        ivThumbnail.setImageBitmap(data.getBitmap());

        TextView tvAuthor = listItemView.findViewById(R.id.tvAuthor);
        tvAuthor.setText(data.getAuthor());

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "List Item Clicked", Toast.LENGTH_SHORT).show();
                Uri webpage = Uri.parse(data.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                context.startActivity(intent);
            }
        });

        return listItemView;
    }

}
