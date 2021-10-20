package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.Transition;
import androidx.transition.a;

public abstract class ai extends Transition {
    private static final String[] a = {"android:visibility:visibility", "android:visibility:parent"};
    private int b = 3;

    public Animator a(ViewGroup viewGroup, View view, s sVar, s sVar2) {
        return null;
    }

    public Animator b(ViewGroup viewGroup, View view, s sVar, s sVar2) {
        return null;
    }

    /* access modifiers changed from: private */
    public static class b {
        boolean a;
        boolean b;
        int c;
        int d;
        ViewGroup e;
        ViewGroup f;

        b() {
        }
    }

    public void a(int i) {
        if ((i & -4) == 0) {
            this.b = i;
            return;
        }
        throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
    }

    @Override // androidx.transition.Transition
    public String[] getTransitionProperties() {
        return a;
    }

    private void a(s sVar) {
        sVar.a.put("android:visibility:visibility", Integer.valueOf(sVar.b.getVisibility()));
        sVar.a.put("android:visibility:parent", sVar.b.getParent());
        int[] iArr = new int[2];
        sVar.b.getLocationOnScreen(iArr);
        sVar.a.put("android:visibility:screenLocation", iArr);
    }

    @Override // androidx.transition.Transition
    public void captureStartValues(s sVar) {
        a(sVar);
    }

    @Override // androidx.transition.Transition
    public void captureEndValues(s sVar) {
        a(sVar);
    }

    private b a(s sVar, s sVar2) {
        b bVar = new b();
        bVar.a = false;
        bVar.b = false;
        if (sVar == null || !sVar.a.containsKey("android:visibility:visibility")) {
            bVar.c = -1;
            bVar.e = null;
        } else {
            bVar.c = ((Integer) sVar.a.get("android:visibility:visibility")).intValue();
            bVar.e = (ViewGroup) sVar.a.get("android:visibility:parent");
        }
        if (sVar2 == null || !sVar2.a.containsKey("android:visibility:visibility")) {
            bVar.d = -1;
            bVar.f = null;
        } else {
            bVar.d = ((Integer) sVar2.a.get("android:visibility:visibility")).intValue();
            bVar.f = (ViewGroup) sVar2.a.get("android:visibility:parent");
        }
        if (sVar == null || sVar2 == null) {
            if (sVar == null && bVar.d == 0) {
                bVar.b = true;
                bVar.a = true;
            } else if (sVar2 == null && bVar.c == 0) {
                bVar.b = false;
                bVar.a = true;
            }
        } else if (bVar.c == bVar.d && bVar.e == bVar.f) {
            return bVar;
        } else {
            if (bVar.c != bVar.d) {
                if (bVar.c == 0) {
                    bVar.b = false;
                    bVar.a = true;
                } else if (bVar.d == 0) {
                    bVar.b = true;
                    bVar.a = true;
                }
            } else if (bVar.f == null) {
                bVar.b = false;
                bVar.a = true;
            } else if (bVar.e == null) {
                bVar.b = true;
                bVar.a = true;
            }
        }
        return bVar;
    }

    @Override // androidx.transition.Transition
    public Animator createAnimator(ViewGroup viewGroup, s sVar, s sVar2) {
        b a2 = a(sVar, sVar2);
        if (!a2.a) {
            return null;
        }
        if (a2.e == null && a2.f == null) {
            return null;
        }
        if (a2.b) {
            return a(viewGroup, sVar, a2.c, sVar2, a2.d);
        }
        return b(viewGroup, sVar, a2.c, sVar2, a2.d);
    }

    public Animator a(ViewGroup viewGroup, s sVar, int i, s sVar2, int i2) {
        if ((this.b & 1) != 1 || sVar2 == null) {
            return null;
        }
        if (sVar == null) {
            View view = (View) sVar2.b.getParent();
            if (a(getMatchedTransitionValues(view, false), getTransitionValues(view, false)).a) {
                return null;
            }
        }
        return a(viewGroup, sVar2.b, sVar, sVar2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:44:0x008a A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00f1 A[RETURN] */
    public Animator b(ViewGroup viewGroup, s sVar, int i, s sVar2, int i2) {
        int id;
        if ((this.b & 2) != 2) {
            return null;
        }
        final View view = sVar != null ? sVar.b : null;
        View view2 = sVar2 != null ? sVar2.b : null;
        if (view2 == null || view2.getParent() == null) {
            if (view2 != null) {
                view = view2;
                view2 = null;
            } else {
                if (view != null) {
                    if (view.getParent() != null) {
                        if (view.getParent() instanceof View) {
                            View view3 = (View) view.getParent();
                            if (!a(getTransitionValues(view3, true), getMatchedTransitionValues(view3, true)).a) {
                                view = q.a(viewGroup, view, view3);
                            } else if (view3.getParent() != null || (id = view3.getId()) == -1 || viewGroup.findViewById(id) == null || !this.mCanRemoveViews) {
                                view = null;
                            }
                            view2 = null;
                        }
                    }
                }
                view = null;
                view2 = null;
            }
            if (view == null && sVar != null) {
                int[] iArr = (int[]) sVar.a.get("android:visibility:screenLocation");
                int i3 = iArr[0];
                int i4 = iArr[1];
                int[] iArr2 = new int[2];
                viewGroup.getLocationOnScreen(iArr2);
                view.offsetLeftAndRight((i3 - iArr2[0]) - view.getLeft());
                view.offsetTopAndBottom((i4 - iArr2[1]) - view.getTop());
                final w a2 = x.a(viewGroup);
                a2.a(view);
                Animator b2 = b(viewGroup, view, sVar, sVar2);
                if (b2 == null) {
                    a2.b(view);
                } else {
                    b2.addListener(new AnimatorListenerAdapter() {
                        /* class androidx.transition.ai.AnonymousClass1 */

                        public void onAnimationEnd(Animator animator) {
                            a2.b(view);
                        }
                    });
                }
                return b2;
            } else if (view2 != null) {
                return null;
            } else {
                int visibility = view2.getVisibility();
                ad.a(view2, 0);
                Animator b3 = b(viewGroup, view2, sVar, sVar2);
                if (b3 != null) {
                    a aVar = new a(view2, i2, true);
                    b3.addListener(aVar);
                    a.a(b3, aVar);
                    addListener(aVar);
                } else {
                    ad.a(view2, visibility);
                }
                return b3;
            }
        } else if (i2 == 4 || view == view2) {
            view = null;
            if (view == null) {
            }
            if (view2 != null) {
            }
        } else if (!this.mCanRemoveViews) {
            view = q.a(viewGroup, view, (View) view.getParent());
            view2 = null;
            if (view == null) {
            }
            if (view2 != null) {
            }
        }
        view2 = null;
        if (view == null) {
        }
        if (view2 != null) {
        }
    }

    @Override // androidx.transition.Transition
    public boolean isTransitionRequired(s sVar, s sVar2) {
        if (sVar == null && sVar2 == null) {
            return false;
        }
        if (sVar != null && sVar2 != null && sVar2.a.containsKey("android:visibility:visibility") != sVar.a.containsKey("android:visibility:visibility")) {
            return false;
        }
        b a2 = a(sVar, sVar2);
        if (!a2.a) {
            return false;
        }
        if (a2.c == 0 || a2.d == 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static class a extends AnimatorListenerAdapter implements Transition.d, a.AbstractC٠٠٥١a {
        boolean a = false;
        private final View b;
        private final int c;
        private final ViewGroup d;
        private final boolean e;
        private boolean f;

        @Override // androidx.transition.Transition.d
        public void a(Transition transition) {
        }

        @Override // androidx.transition.Transition.d
        public void e(Transition transition) {
        }

        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationStart(Animator animator) {
        }

        a(View view, int i, boolean z) {
            this.b = view;
            this.c = i;
            this.d = (ViewGroup) view.getParent();
            this.e = z;
            a(true);
        }

        @Override // androidx.transition.a.AbstractC٠٠٥١a
        public void onAnimationPause(Animator animator) {
            if (!this.a) {
                ad.a(this.b, this.c);
            }
        }

        @Override // androidx.transition.a.AbstractC٠٠٥١a
        public void onAnimationResume(Animator animator) {
            if (!this.a) {
                ad.a(this.b, 0);
            }
        }

        public void onAnimationCancel(Animator animator) {
            this.a = true;
        }

        public void onAnimationEnd(Animator animator) {
            a();
        }

        @Override // androidx.transition.Transition.d
        public void b(Transition transition) {
            a();
            transition.removeListener(this);
        }

        @Override // androidx.transition.Transition.d
        public void c(Transition transition) {
            a(false);
        }

        @Override // androidx.transition.Transition.d
        public void d(Transition transition) {
            a(true);
        }

        private void a() {
            if (!this.a) {
                ad.a(this.b, this.c);
                ViewGroup viewGroup = this.d;
                if (viewGroup != null) {
                    viewGroup.invalidate();
                }
            }
            a(false);
        }

        private void a(boolean z) {
            ViewGroup viewGroup;
            if (this.e && this.f != z && (viewGroup = this.d) != null) {
                this.f = z;
                x.a(viewGroup, z);
            }
        }
    }
}
