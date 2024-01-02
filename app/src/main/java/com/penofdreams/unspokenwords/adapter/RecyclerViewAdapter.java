package com.penofdreams.unspokenwords.adapter;



import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.penofdreams.unspokenwords.databinding.ItemViewLayoutBinding;
import com.penofdreams.unspokenwords.model.MessageModel;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{


    private List<MessageModel> messageModelList;
    private Context context;
    private static final String TAG = "flow -> RecyclerViewAdapter";

    public RecyclerViewAdapter(List<MessageModel> messageModelList, Context context) {
        this.messageModelList = messageModelList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemViewLayoutBinding binding = ItemViewLayoutBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.bindViews(position);

    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemViewLayoutBinding binding;
        String label = "Email";
        String text ;
        int position;
        public ViewHolder(ItemViewLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindViews(int position) {

            String message = messageModelList.get(position).getMessage();
            String name = messageModelList.get(position).getName();
            String email = messageModelList.get(position).getEmail();

           // Log.d(TAG, "bindViews: " + message + " " + name + " " + email + "");

            this.position = position;

            binding.cardAssignmentTextView.setText(message);
            binding.cardUsernameView.setText(name);
            binding.cardEmailView.setText(email);
            binding.cardSendEmailButton.setOnClickListener(this);
            binding.cardCopyButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == binding.cardCopyButton)
            {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(label, messageModelList.get(position).getEmail() );
                clipboard.setPrimaryClip(clip);
            }
        }
    }
}
