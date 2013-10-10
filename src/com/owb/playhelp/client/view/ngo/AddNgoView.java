package com.owb.playhelp.client.view.ngo;

import com.owb.playhelp.client.presenter.ngo.AddNgoPresenter;
import com.owb.playhelp.client.view.AddDBRecordView;

public class AddNgoView extends AddDBRecordView implements AddNgoPresenter.Display {

	public AddNgoView() {
		super();
		this.getPageTitleField().setText("Organization Information");
		
		String description="<font style='font-family:Century Gothic; font-size: 15px; color: #999;'>" +
			"Our main purpose is to create a pathway of children to dignified life. For this purpose, we identify worldwide the needs "+
			"of group of abandoned and orphan children. This information is shared with organizations that could potentially provide" +
			"the resources necessary to respond to their needs. Moreover, our OWB community provides support with ideas, funding or "+
			"raising awareness about these child's needs. In addition, we support projects that are engaged to helping these children "+
			"in need. We can specially help those projects of individuals or small organizations that do not have the resources "+
			"of a large organizations and struggle connecting to people and resources. We connect them to the organizations in our "+ 
			"network and advice them about their projects. <br></br>"+
			"Since our main goal is to help the children, we guaranty that the goal of the efforts we support have always in mind the welfare "+
			"of the children and their main purpose is to improve their life quality. We follow up projects closely to verifying "+
			"that they are proceeding and performing as expected. <br></br></font>";
		description="<font style='font-family:Century Gothic; font-size: 15px; color: #999;'>" +
					"Companies, non-profits, agencies, and individuals who want to help represent a resource, and this network connects resources to needs. " +
					"Our ultimate goal is to help fulfill the essential needs of all orphans by connecting them to the right resources. <br></br>" +
					"We gather information about the needs of orphan and abandoned children around the world and share it " +
					"with organizations, agencies, companies and individuals who could potentially provide the resources that would " +
					"be necessary to meet those needs. Moreover, the members of our network (big and small) support each other with " +
					"ideas, feedback on projects, expert advice, fundraising, or raising awareness about these children's needs. " +
					"We can provide support the projects of individuals or small organizations that may struggle connecting to people, " +
					"resources, and expertise. <br></br>" +
					"We aim to provide support to the projects whose goals are to improve these children's quality of life, " +
					"and we follow the projects closely to verify that they are proceeding and performing as expected. " +
					"If you agree that collaboration is key to global change, join our network now and work with others to support " +
					"projects, share expertise, and provide feedback.</font>";
		this.getPageDescriptionField().setHTML(description);
	}
	
}
