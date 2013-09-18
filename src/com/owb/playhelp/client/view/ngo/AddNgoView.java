package com.owb.playhelp.client.view.ngo;

import com.owb.playhelp.client.presenter.ngo.AddNgoPresenter;
import com.owb.playhelp.client.view.AddDBRecordView;

public class AddNgoView extends AddDBRecordView implements AddNgoPresenter.Display {

	public AddNgoView() {
		super();
		this.getPageTitleField().setText("Organization Information");
		
		String description="Our main purpose is to create a pathway of children to dignified life. For this purpose, we identify worldwide the needs "+
			"of group of abandoned and orphan children. This information is shared with organizations that could potentially provide" +
			"the resources necessary to respond to their needs. Moreover, our OWB community provides support with ideas, funding or "+
			"raising awareness about these child's needs. In addition, we support projects that are engaged to helping these children "+
			"in need. We can specially help those projects of individuals or small organizations that do not have the resources "+
			"of a large organizations and struggle connecting to people and resources. We connect them to the organizations in our "+ 
			"network and advice them about their projects. <br></br>"+
			"Since our main goal is to help the children, we guaranty that the goal of the efforts we support have always in mind the welfare "+
			"of the children and their main purpose is to improve their life quality. We follow up projects closely to verifying "+
			"that they are proceeding and performing as expected. <br></br>";
		this.getPageDescriptionField().setHTML(description);
	}
	
}
