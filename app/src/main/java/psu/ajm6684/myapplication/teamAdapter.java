package psu.ajm6684.myapplication;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;



import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Random;

public class teamAdapter extends FirestoreRecyclerAdapter<Teams, teamAdapter.teamHolder> {

    private onItemClickListener listener;





    public teamAdapter(@NonNull FirestoreRecyclerOptions<Teams> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull teamHolder teamHolder, int i, @NonNull Teams team) {


        teamHolder.teamNameView.setText(team.getTeamName());
        teamHolder.guardView.setText(team.getGuard());
        teamHolder.guardForwardView.setText((team.getGuardForward()));
        teamHolder.forwardGuardView.setText(team.getForwardGuard());
        teamHolder.centerForwardView.setText(team.getForwardCenter());
        teamHolder.centerView.setText(team.getCenter());





    }

    @NonNull
    @Override
    public teamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.teamitem,parent,false);

        return new teamHolder(v);
    }

//    public void deletePatient(int position){
//        getSnapshots().getSnapshot(position).getReference().delete();
//
//
//
//    }
//
//    public void updateNurse(int position,String newNurse){
//
//        getSnapshots().getSnapshot(position).getReference().update("activeNurse",newNurse);
//    }








    class teamHolder extends RecyclerView.ViewHolder{
        TextView teamNameView;
        TextView guardView;
        TextView forwardGuardView;
        TextView guardForwardView;
        TextView centerForwardView;
        TextView centerView;



        public teamHolder(final View itemView){
            super(itemView);
            teamNameView = itemView.findViewById(R.id.text_view_title);
            guardView = itemView.findViewById(R.id.text_view_guard);
            forwardGuardView = itemView.findViewById(R.id.text_view_fowardGuard);
            guardForwardView = itemView.findViewById(R.id.text_view_guardForward);
            centerForwardView = itemView.findViewById(R.id.text_view_centerForward);
            centerView = itemView.findViewById(R.id.text_view_center);


//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//
//                    int position = getAdapterPosition();
//
//                    listener.onItemLongClick(getSnapshots().getSnapshot(position),position);
//                    return true;
//                }
//            });
////
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//
//                    if(position != RecyclerView.NO_POSITION && listener != null){
//
//                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
//
//
//                    }
//                    // getSnapshots().getSnapshot(position).getReference()
//                }
//            });

        }
    }

    public interface onItemClickListener {

        void onItemClick(DocumentSnapshot documentSnapshot, int position);
        void onItemLongClick(DocumentSnapshot documentSnapshot, int position);

    }

    public void setOnItemClickListener(onItemClickListener listener){

        this.listener = listener;

    }


}
