package com.owb.playhelp.client.view.orphanage;

import com.owb.playhelp.client.presenter.orphanage.AddOrphanagePresenter;
import com.owb.playhelp.client.view.AddDBRecordView;

public class AddOrphanageView extends AddDBRecordView implements AddOrphanagePresenter.Display {

	public AddOrphanageView() {
		super();
		this.getPageTitleField().setText("Orphanage Information");
		
		String description="We work with organizations and orphanages that start projects matching our standards. "+
		            "We support them and represent them in the community and within our network if they follow "+
		            "the standards in any of the areas they are trying to help. "+
		            "We guaranty that the projects that we support work toward the goals we defined "+ 
		            "for each of these areas. These goals were defined as a reference that we believe is ideal to "+ 
		            "allow children grow in an appropriate environment that help them build their future as deserved "+ 
		            "by any human being. Although we are aware that these goals are challenging and utopique, we believe "+ 
		            "that having them as a reference will help to always know the way our efforts must go.<br></br>";
		this.getPageDescriptionField().setHTML(description);
	}

	
}
