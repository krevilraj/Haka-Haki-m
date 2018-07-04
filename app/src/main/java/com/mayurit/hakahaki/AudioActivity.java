package com.mayurit.hakahaki;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;

import at.blogc.android.views.ExpandableTextView;

public class AudioActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        expandText("देशको पूर्वी भूभाग भएर बहने नदीहरूमा बाढी आउन सक्ने संभावना बढेको छ । जल तथा मौसम विज्ञान विभागको जलविज्ञान महाशाखा तथा बाढी पूर्वानुमान शाखाले जारी गरेको बाढी बुलेटिन ");
    }
    private void expandText(String information) {
        final ExpandableTextView expandableTextView = (ExpandableTextView) this.findViewById(R.id.expandableTextView);
        expandableTextView.setText(information);
        final ImageButton buttonToggle = (ImageButton) this.findViewById(R.id.button_toggle);
        expandableTextView.setAnimationDuration(750L);
        expandableTextView.setInterpolator(new OvershootInterpolator());
        expandableTextView.setExpandInterpolator(new OvershootInterpolator());
        expandableTextView.setCollapseInterpolator(new OvershootInterpolator());

        buttonToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                expandableTextView.toggle();
                float deg = buttonToggle.getRotation() + 180F;
                buttonToggle.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());

            }
        });
    }

}
