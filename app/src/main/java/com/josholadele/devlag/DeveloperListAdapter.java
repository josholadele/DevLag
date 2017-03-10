package com.josholadele.devlag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Oladele on 3/9/17.
 */

public class DeveloperListAdapter
        extends RecyclerView.Adapter<DeveloperListAdapter.ViewHolder> {

    private final List<Developer> mValues;
    boolean mTwoPane = false;
    FragmentManager fragmentManager;
    Context mContext;


    public DeveloperListAdapter(Context context, List<Developer> items, FragmentManager fragmentManager, boolean hasTwoPane) {
        mValues = items;
        mContext = context;
        mTwoPane = hasTwoPane;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.developer_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).getUsername());
        Glide.with(mContext).load(mValues.get(position).getPhotoUrl())/**/
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .fallback(R.drawable.avatar_contact)
                .into(holder.profileImage);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(DeveloperDetailFragment.ARG_ITEM_ID, holder.mItem.getUsername());
                    DeveloperDetailFragment fragment = new DeveloperDetailFragment();
                    fragment.setArguments(arguments);
                    fragmentManager.beginTransaction()
                            .replace(R.id.developer_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DeveloperDetailActivity.class);
                    intent.putExtra(DeveloperDetailFragment.ARG_ITEM_ID, holder.mItem);

                    //intent.putExtra()
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CircleImageView profileImage;
        //public final TextView mIdView;
        public final TextView mContentView;
        public Developer mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            profileImage = (CircleImageView) view.findViewById(R.id.profileImage);
            //mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
