package com.logictree.supply.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.logictree.supply.R;
import com.logictree.supply.activities.NewTecApp;
import com.logictree.supply.models.Department;

public class DepartmentsFragment extends Fragment implements
AdapterView.OnItemClickListener, PopupWindow.OnDismissListener {

	private static final String TAG = "DepartmentsFragment";
	private ListPopupWindow mListPopupWindow;
	private ViewGroup mRootView;
	private TextView mTitle;
	private TextView mAbstract;
	private ArrayAdapter<Department> adapter;
	private List<Department> departments;
	private DepartmentSelectedListener departmentSelectedListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		departments = new ArrayList<Department>();
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getDepartments();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		adapter = new ArrayAdapter<Department>(getActivity(),
				R.layout.layout_department_fragment, departments);
		mRootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_tracks_dropdown, null);
		mTitle = (TextView) mRootView.findViewById(R.id.track_title);
		mTitle.setText("Department");

		mAbstract = (TextView) mRootView.findViewById(R.id.track_abstract);
		mAbstract.setText("All");
		mRootView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				mListPopupWindow = new ListPopupWindow(getActivity());

				mListPopupWindow.setAdapter(adapter);
				mListPopupWindow.setModal(true);
				// mListPopupWindow.setContentWidth(400);
				mListPopupWindow.setAnchorView(mRootView);
				mListPopupWindow
				.setOnItemClickListener(DepartmentsFragment.this);
				mListPopupWindow.show();
				mListPopupWindow.setOnDismissListener(DepartmentsFragment.this);
			}
		});
		return mRootView;
	}

	public void onDismiss() {
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			departmentSelectedListener = (DepartmentSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement ListItemSelectedListener in Activity");
		}
	}

	public interface DepartmentSelectedListener {
		public void onDeptSelected(String deptname);
	}

	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		String deptname = ((TextView) view).getText().toString().trim();
		mAbstract.setText(deptname);
		mListPopupWindow.dismiss();
		departmentSelectedListener.onDeptSelected(deptname);
	}

	private void getDepartments() {
		Log.v(TAG,"getDept");
		final NewTecApp app = (NewTecApp) getActivity().getApplication();
		departments = app.shareDatabaseInstance().getDepartments((app.getUserId().trim()));
		if(departments != null && departments.size() > 0) {
			departments.add(0, new Department("0", "All", "Active"));
			adapter = new ArrayAdapter<Department>(getActivity(),
					R.layout.layout_department_fragment, departments);
		}
	}

	/*public void refreshDepartments(final List<Department> departmentslist) {
		Log.v(TAG,"refresh dept");
		if(departmentslist != null && departmentslist.size()> 0){
			mAbstract.setText("All");
			departmentslist.add(0, new Department("0", "All", "Active"));
			adapter = new ArrayAdapter<Department>(getActivity(),
					R.layout.layout_department_fragment, departmentslist);
			adapter.notifyDataSetChanged();
		}
	}*/
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
