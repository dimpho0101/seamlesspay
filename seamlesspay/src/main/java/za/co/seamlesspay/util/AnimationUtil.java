package za.co.seamlesspay.util;

import com.airbnb.lottie.LottieAnimationView;

/**
 * Created by Lebogang Nkosi on 28/04/2019.
 */

public class AnimationUtil {


    /**
     * Play the animation using Lottie. This library uses .json files to display animations.
     */
    public void playAnimation(LottieAnimationView lottieAnimationView, String jsonAnimation) {
        lottieAnimationView.setAnimation(jsonAnimation);
        lottieAnimationView.playAnimation();
    }


    /**
     * Pause the animation.
     */
    public void pauseAnimation(LottieAnimationView lottieAnimationView) {
        lottieAnimationView.pauseAnimation();
    }
}
