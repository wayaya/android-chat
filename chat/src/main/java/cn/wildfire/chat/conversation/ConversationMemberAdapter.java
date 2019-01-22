package cn.wildfire.chat.conversation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import cn.wildfirechat.chat.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wildfirechat.model.UserInfo;

public class ConversationMemberAdapter extends RecyclerView.Adapter<ConversationMemberAdapter.MemberViewHolder> {
    private List<UserInfo> members;
    private boolean enableAddMember;
    private boolean enableRemoveMember;
    private OnMemberClickListener onMemberClickListener;

    public ConversationMemberAdapter(boolean enableAddMember, boolean enableRemoveMember) {
        this.enableAddMember = enableAddMember;
        this.enableRemoveMember = enableRemoveMember;
    }

    public void setMembers(List<UserInfo> members) {
        this.members = members;
    }


    public void addMembers(List<UserInfo> members) {
        int startIndex = this.members.size();
        this.members.addAll(members);
        notifyItemRangeInserted(startIndex, members.size());
    }

    public void removeMembers(List<String> memberIds) {
        List<Integer> indexes = new ArrayList<>(memberIds.size());
        for (int j = 0; j < memberIds.size(); j++) {
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).uid.equals(memberIds.get(j))) {
                    indexes.add(i);
                    break;
                }
            }
        }

        for (int index : indexes) {
            members.remove(index);
            notifyItemRemoved(index);
        }
    }

    public void setOnMemberClickListener(OnMemberClickListener onMemberClickListener) {
        this.onMemberClickListener = onMemberClickListener;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.conversation_item_member_info, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        if (position < members.size()) {
            holder.bindUserInfo(members.get(position));
        } else {
            if (position == members.size()) {
                if (enableAddMember) {
                    holder.bindAddMember();
                } else if (enableRemoveMember) {
                    holder.bindRemoveMember();
                }
            } else if (position == members.size() + 1 && enableRemoveMember) {
                holder.bindRemoveMember();
            }
        }
    }

    @Override
    public int getItemCount() {
        if (members == null) {
            return 0;
        }
        int count = members.size();
        if (enableAddMember) {
            count++;
        }
        if (enableRemoveMember) {
            count++;
        }
        return count;
    }

    class MemberViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.portraitImageView)
        ImageView portraitImageView;
        @Bind(R.id.nameTextView)
        TextView nameTextView;
        private UserInfo userInfo;
        private int type = TYPE_USER;
        private static final int TYPE_USER = 0;
        private static final int TYPE_ADD = 1;
        private static final int TYPE_REMOVE = 2;

        @OnClick(R.id.portraitImageView)
        void onClick() {
            if (onMemberClickListener == null) {
                return;
            }
            switch (type) {
                case TYPE_USER:
                    onMemberClickListener.onUserMemberClick(userInfo);
                    break;
                case TYPE_ADD:
                    onMemberClickListener.onAddMemberClick();
                    break;
                case TYPE_REMOVE:
                    onMemberClickListener.onRemoveMemberClick();
                    break;
                default:
                    break;
            }
        }

        public MemberViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindUserInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
            this.type = TYPE_USER;
            nameTextView.setVisibility(View.VISIBLE);
            nameTextView.setText(userInfo.displayName);
            Glide.with(portraitImageView).load(userInfo.portrait).apply(new RequestOptions().centerCrop().placeholder(R.mipmap.default_header)).into(portraitImageView);
        }

        public void bindAddMember() {
            nameTextView.setVisibility(View.GONE);
            portraitImageView.setImageResource(R.mipmap.ic_add_team_member);
            this.type = TYPE_ADD;

        }

        public void bindRemoveMember() {
            nameTextView.setVisibility(View.GONE);
            portraitImageView.setImageResource(R.mipmap.ic_remove_team_member);
            this.type = TYPE_REMOVE;
        }
    }

    public interface OnMemberClickListener {
        void onUserMemberClick(UserInfo userInfo);

        void onAddMemberClick();

        void onRemoveMemberClick();
    }
}