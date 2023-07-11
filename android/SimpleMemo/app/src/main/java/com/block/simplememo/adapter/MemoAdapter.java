package com.block.simplememo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.block.simplememo.R;
import com.block.simplememo.model.Memo;

import java.util.ArrayList;

// 2. 어댑터 클래스를 상속받는다.
public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder>  {

    // 3. 위에서 상속받으면 에러가 뜬다.
    //    오버라이딩 함수를 작성한다.


    // 4. 이 클래스의 멤버변수를 작성한다. 기본 2개는 필수!
    Context context;
    ArrayList<Memo> memoList;

    // 5. 위의 멤버변수를 셋팅할수 있는 생성자 만든다.
    public MemoAdapter(Context context, ArrayList<Memo> memoList) {
        this.context = context;
        this.memoList = memoList;
    }

    // 6. 아래의 함수 3개를 구현한다.


    // 행 화면(memo_row.xml 파일) 을 연결시키는 함수.
    @NonNull
    @Override
    public MemoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.memo_row, parent, false);

        return new MemoAdapter.ViewHolder(view) ;
    }

    // 실제로 데이터를 화면에 적용시키는 함수
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Memo memo = memoList.get(position);

        holder.txtContent.setText( memo.content  );

    }

    // 데이터의 갯수 ( = 행의 갯수 )
    @Override
    public int getItemCount() {
        return memoList.size();
    }



    // 1. 뷰홀더 클래스를 만든다.  inner class
    //    이클래스에는, 행화면에 있는 뷰들을 여기서 연결시킨다.
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtContent = itemView.findViewById(R.id.txtContent);

        }
    }

}
