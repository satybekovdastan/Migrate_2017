package com.migrate.admin.pagination.Adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.migrate.admin.pagination.R;
import com.migrate.admin.pagination.Serializables.Diaspora;

import java.util.List;

/**
 * Created by ulan on 11/12/16.
 */
public class RVHHHHAdapter extends RecyclerView.Adapter<RVHHHHAdapter.ViewHolder> {

    private List<Diaspora> records;
    public Context context;
    public RVHHHHAdapter(List<Diaspora> records, Context context) {
        this.records = records;
        this.context=context;
    }

    /**
     * Создание новых View и ViewHolder элемента списка, которые впоследствии могут переиспользоваться.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv_diaspora, viewGroup, false);
        return new ViewHolder(v);
    }

    /**
     * Заполнение виджетов View данными из элемента списка с номером i
     */
    @Override
    public void onBindViewHolder(ViewHolder view, int i) {
        Diaspora singleStudent = records.get(i);
       view.tvManager.setText(singleStudent.getManager());
        view.tvNumber.setText(singleStudent.getNumber());
        if (singleStudent.getAddress().equals("")) view.linearLayout.setVisibility(View.GONE);
        view. tvAddress.setText(singleStudent.getAddress());
        view.tvNumber1.setText(singleStudent.getEmail());
        view.tvName.setText(singleStudent.getCity());

    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    /**
     * Реализация класса ViewHolder, хранящего ссылки на виджеты.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public LinearLayout linearLayout;
        public TextView tvAddress,tvManager,tvNumber,tvNumber1,tvNumber2;

        public Diaspora student;
        public ViewHolder(View v) {
            super(v);
            tvName=(TextView) v.findViewById(R.id.tv_name_employment) ;
            tvAddress=(TextView) v.findViewById(R.id.tv_address_second) ;
            tvManager=(TextView) v.findViewById(R.id.tv_manager_employment) ;
            tvNumber=(TextView) v.findViewById(R.id.tv_phone_number_second) ;
            tvNumber1=(TextView) v.findViewById(R.id.tv_phone_number1_second) ;
            linearLayout=(LinearLayout) v.findViewById(R.id.linear_diaspora) ;

            tvNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",records.get(getAdapterPosition()).getNumber() , null)));
                }
            });
            tvAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder ad;
                    ad = new AlertDialog.Builder(context);

                    ad.setPositiveButton(context.getString(R.string.toast_karta), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(student.getKarta()));
                            context.startActivity(browserIntent);
                        }
                    });
                    ad.setNegativeButton(context.getString(R.string.toast_copy), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("",tvAddress.getText() );
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(context, R.string.toast_copy_to_buffer,Toast.LENGTH_SHORT).show();
                        }
                    });
                    ad.setCancelable(true);
                    ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        public void onCancel(DialogInterface dialog) {

                        }
                    });

                }
            });
            tvNumber1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                    emailIntent.setType("plain/text");
                    // Кому
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                            new String[] { tvNumber1.getText().toString() });

                    // Поехали!
                    context.startActivity(Intent.createChooser(emailIntent,
                            "Отправка письма..."));
                }
            });
        }
    }
}
