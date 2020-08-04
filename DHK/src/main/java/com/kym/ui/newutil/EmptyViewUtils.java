package com.kym.ui.newutil;


import com.kym.ui.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Desction: Author:pengjianbo Date:16/3/9 上午11:54
 */
public class EmptyViewUtils {

	public static void showLoading(ViewGroup emptyView) {
		if (emptyView == null) {
			return;
		}
		ProgressBar pbLoading = emptyView
				.findViewById(R.id.pb_loading);
		pbLoading.setVisibility(View.VISIBLE);
	}

	public static void showNetErrorEmpty(ViewGroup emptyView) {
		if (emptyView == null) {
			return;
		}
		View layout_empty = emptyView.findViewById(R.id.layout_empty);
		layout_empty.setVisibility(View.VISIBLE);
	}

	public static void goneNetErrorEmpty(ViewGroup emptyView) {
		if (emptyView == null) {
			return;
		}
		View layout_empty = emptyView.findViewById(R.id.layout_empty);
		layout_empty.setVisibility(View.GONE);
	}

	public static void goneNoDataEmpty(ViewGroup emptyView) {
		if (emptyView == null) {
			return;
		}
		View tv_null = emptyView.findViewById(R.id.tv_null);
		tv_null.setVisibility(View.GONE);
	}

	public static void goneNoDataEmpty1(ViewGroup emptyView) {
		if (emptyView == null) {
			return;
		}
		View tv_null = emptyView.findViewById(R.id.tv_nulls);
		tv_null.setVisibility(View.GONE);
	}

	public static void showNoDataEmpty(ViewGroup emptyView) {
		if (emptyView == null) {
			return;
		}
		TextView tv_null = emptyView.findViewById(R.id.tv_null);
		tv_null.setVisibility(View.VISIBLE);
	}

	public static void showNoDataEmpty1(ViewGroup emptyView) {
		if (emptyView == null) {
			return;
		}
		TextView tv_null = emptyView.findViewById(R.id.tv_nulls);
		tv_null.setVisibility(View.VISIBLE);
	}	public static void showNoDataEmpty1(ViewGroup emptyView,String s) {
		if (emptyView == null) {
			return;
		}
		TextView tv_null = emptyView.findViewById(R.id.tv_nulls);
		tv_null.setText(s);
		tv_null.setVisibility(View.VISIBLE);
	}
}
