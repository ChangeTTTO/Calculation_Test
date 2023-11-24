package com.example.calculationtest;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.calculationtest.R;

import com.example.calculationtest.databinding.FragmentQuestionBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(String param1, String param2) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MyViewModel myViewModel;
        myViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(requireActivity().getApplication(), this)).get(MyViewModel.class);
        //在Fragments中写这一段
        FragmentQuestionBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_question , container, false);
        binding.setData(myViewModel);
        //如果是在activity中直接this,如果实在Fragments的话需要getActivity()
        //getActivity() 返回的是 Fragment 所关联的 Activity 的实例。
        binding.setLifecycleOwner(requireActivity()); //todo
        StringBuilder builder=new StringBuilder(); //可变字符串

        //拿到一个监听对象。
       // View是用户界面上的可视元素的基类。所有用户界面上的控件，如按钮、文本框、图像等，都是View类的子类。

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int viewId = view.getId();
                if (viewId == R.id.button0) {
                    builder.append("0");
                } else if (viewId == R.id.button1) {
                    builder.append("1");
                } else if (viewId == R.id.button2) {
                    builder.append("2");
                } else if (viewId == R.id.button3) {
                    builder.append("3");
                } else if (viewId == R.id.button4) {
                    builder.append("4");
                } else if (viewId == R.id.button5) {
                    builder.append("5");
                } else if (viewId == R.id.button6) {
                    builder.append("6");
                } else if (viewId == R.id.button7) {
                    builder.append("7");
                } else if (viewId == R.id.button8) {
                    builder.append("8");
                } else if (viewId == R.id.button9) {
                    builder.append("9");
                }else if(viewId == R.id.buttonClear){
                    builder.setLength(0);
                }
                if (builder.length()==0){
                    binding.textView9.setText(getString(R.string.input_indicator));
                }else {
                    //builder.toString() 将返回拼接好的字符串，例如 "012"
                    binding.textView9.setText(builder.toString());
                }
            }
        };
        binding.button0.setOnClickListener(listener);
        binding.button1.setOnClickListener(listener);
        binding.button2.setOnClickListener(listener);
        binding.button3.setOnClickListener(listener);
        binding.button4.setOnClickListener(listener);
        binding.button5.setOnClickListener(listener);
        binding.button6.setOnClickListener(listener);
        binding.button7.setOnClickListener(listener);
        binding.button8.setOnClickListener(listener);
        binding.button9.setOnClickListener(listener);
        binding.buttonClear.setOnClickListener(listener);
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(builder.toString()).intValue()==myViewModel.getAnswer().getValue()){
                        myViewModel.answerCorrect();
                        builder.setLength(0);
                        builder.append(getString(R.string.answer_correct_message));
                }else {
                    NavController controller = Navigation.findNavController(view);
                    if (myViewModel.win_flag){
                    controller.navigate(R.id.action_questionFragment_to_winFragment);
                    myViewModel.win_flag=false;
                    myViewModel.save();
                    }else {
                        controller.navigate(R.id.action_questionFragment_to_loseFragment);
                    }
                }
            }
        });
        return binding.getRoot();
    }
}