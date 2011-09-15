package com.megadict.initializer;

import android.content.Context;
import android.text.ClipboardManager;
import android.widget.AutoCompleteTextView;

import com.megadict.bean.BusinessComponent;
import com.megadict.bean.DictionaryComponent;
import com.megadict.business.WordListTask;
import com.megadict.business.WordListTask.OnClickWordListener;
import com.megadict.widget.ResultView;
import com.megadict.widget.ResultView.OnSelectTextListener;

public final class ResultViewInitializer extends AbstractInitializer {
	public ResultViewInitializer(final Context context, final BusinessComponent businessComponent, final DictionaryComponent dictionaryComponent) {
		super(context, businessComponent, dictionaryComponent);
	}

	@Override
	public void init() {
		// Prepare components.
		final ClipboardManager clipboardManager =
				(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		final ResultView resultView = dictionaryComponent.getResultView();
		final AutoCompleteTextView searchBar =
				dictionaryComponent.getSearchBar();
		resultView.setBackgroundColor(0x00000000);
		resultView.setOnSelectTextListener(new OnSelectTextListener() {
			@Override
			public void onSelectText() {
				final String text = clipboardManager.getText().toString();
				final WordListTask task = new WordListTask(context, text);
				task.setOnClickWordListener(new OnClickWordListener() {
					@Override
					public void onClickWord() {
						searchBar.setText(task.getWord());
						preventRecommending();
						doSearching(searchBar.getText().toString());
					}
				});
				task.execute((Void[]) null);
			}
		});
	}
}
