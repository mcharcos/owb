package com.owb.playhelp.client.view.friend;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.friend.FriendsHomePresenter;

public class FriendsHomeView extends Composite implements FriendsHomePresenter.Display {

	private static FriendsHomeViewUiBinder uiBinder = GWT
			.create(FriendsHomeViewUiBinder.class);

	interface FriendsHomeViewUiBinder extends UiBinder<Widget, FriendsHomeView> {
	}

	public FriendsHomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public FriendsHomeView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
