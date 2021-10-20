package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import java.util.ArrayList;

class a {

    /* renamed from: androidx.transition.a$a  reason: collision with other inner class name */
    interface AbstractC٠٠٥١a {
        void onAnimationPause(Animator animator);

        void onAnimationResume(Animator animator);
    }

    static void a(Animator animator, AnimatorListenerAdapter animatorListenerAdapter) {
        if (Build.VERSION.SDK_INT >= 19) {
            animator.addPauseListener(animatorListenerAdapter);
        }
    }

    static void a(Animator animator) {
        if (Build.VERSION.SDK_INT >= 19) {
            animator.pause();
            return;
        }
        ArrayList<Animator.AnimatorListener> listeners = animator.getListeners();
        if (listeners != null) {
            int size = listeners.size();
            for (int i = 0; i < size; i++) {
                Animator.AnimatorListener animatorListener = listeners.get(i);
                if (animatorListener instanceof AbstractC٠٠٥١a) {
                    ((AbstractC٠٠٥١a) animatorListener).onAnimationPause(animator);
                }
            }
        }
    }

    static void b(Animator animator) {
        if (Build.VERSION.SDK_INT >= 19) {
            animator.resume();
            return;
        }
        ArrayList<Animator.AnimatorListener> listeners = animator.getListeners();
        if (listeners != null) {
            int size = listeners.size();
            for (int i = 0; i < size; i++) {
                Animator.AnimatorListener animatorListener = listeners.get(i);
                if (animatorListener instanceof AbstractC٠٠٥١a) {
                    ((AbstractC٠٠٥١a) animatorListener).onAnimationResume(animator);
                }
            }
        }
    }
}
