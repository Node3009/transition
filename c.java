package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.f.r;
import java.util.Map;

public class c extends Transition {
    private static final String[] a = {"android:changeBounds:bounds", "android:changeBounds:clip", "android:changeBounds:parent", "android:changeBounds:windowX", "android:changeBounds:windowY"};
    private static final Property<Drawable, PointF> b = new Property<Drawable, PointF>(PointF.class, "boundsOrigin") {
        /* class androidx.transition.c.AnonymousClass1 */
        private Rect a = new Rect();

        /* renamed from: a */
        public void set(Drawable drawable, PointF pointF) {
            drawable.copyBounds(this.a);
            this.a.offsetTo(Math.round(pointF.x), Math.round(pointF.y));
            drawable.setBounds(this.a);
        }

        /* renamed from: a */
        public PointF get(Drawable drawable) {
            drawable.copyBounds(this.a);
            return new PointF((float) this.a.left, (float) this.a.top);
        }
    };
    private static final Property<a, PointF> c = new Property<a, PointF>(PointF.class, "topLeft") {
        /* class androidx.transition.c.AnonymousClass3 */

        /* renamed from: a */
        public PointF get(a aVar) {
            return null;
        }

        /* renamed from: a */
        public void set(a aVar, PointF pointF) {
            aVar.a(pointF);
        }
    };
    private static final Property<a, PointF> d = new Property<a, PointF>(PointF.class, "bottomRight") {
        /* class androidx.transition.c.AnonymousClass4 */

        /* renamed from: a */
        public PointF get(a aVar) {
            return null;
        }

        /* renamed from: a */
        public void set(a aVar, PointF pointF) {
            aVar.b(pointF);
        }
    };
    private static final Property<View, PointF> e = new Property<View, PointF>(PointF.class, "bottomRight") {
        /* class androidx.transition.c.AnonymousClass5 */

        /* renamed from: a */
        public PointF get(View view) {
            return null;
        }

        /* renamed from: a */
        public void set(View view, PointF pointF) {
            ad.a(view, view.getLeft(), view.getTop(), Math.round(pointF.x), Math.round(pointF.y));
        }
    };
    private static final Property<View, PointF> f = new Property<View, PointF>(PointF.class, "topLeft") {
        /* class androidx.transition.c.AnonymousClass6 */

        /* renamed from: a */
        public PointF get(View view) {
            return null;
        }

        /* renamed from: a */
        public void set(View view, PointF pointF) {
            ad.a(view, Math.round(pointF.x), Math.round(pointF.y), view.getRight(), view.getBottom());
        }
    };
    private static final Property<View, PointF> g = new Property<View, PointF>(PointF.class, "position") {
        /* class androidx.transition.c.AnonymousClass7 */

        /* renamed from: a */
        public PointF get(View view) {
            return null;
        }

        /* renamed from: a */
        public void set(View view, PointF pointF) {
            int round = Math.round(pointF.x);
            int round2 = Math.round(pointF.y);
            ad.a(view, round, round2, view.getWidth() + round, view.getHeight() + round2);
        }
    };
    private static j k = new j();
    private int[] h = new int[2];
    private boolean i = false;
    private boolean j = false;

    @Override // androidx.transition.Transition
    public String[] getTransitionProperties() {
        return a;
    }

    private void a(s sVar) {
        View view = sVar.b;
        if (r.x(view) || view.getWidth() != 0 || view.getHeight() != 0) {
            sVar.a.put("android:changeBounds:bounds", new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            sVar.a.put("android:changeBounds:parent", sVar.b.getParent());
            if (this.j) {
                sVar.b.getLocationInWindow(this.h);
                sVar.a.put("android:changeBounds:windowX", Integer.valueOf(this.h[0]));
                sVar.a.put("android:changeBounds:windowY", Integer.valueOf(this.h[1]));
            }
            if (this.i) {
                sVar.a.put("android:changeBounds:clip", r.z(view));
            }
        }
    }

    @Override // androidx.transition.Transition
    public void captureStartValues(s sVar) {
        a(sVar);
    }

    @Override // androidx.transition.Transition
    public void captureEndValues(s sVar) {
        a(sVar);
    }

    private boolean a(View view, View view2) {
        if (!this.j) {
            return true;
        }
        s matchedTransitionValues = getMatchedTransitionValues(view, true);
        if (matchedTransitionValues == null) {
            if (view == view2) {
                return true;
            }
            return false;
        } else if (view2 == matchedTransitionValues.b) {
            return true;
        } else {
            return false;
        }
    }

    @Override // androidx.transition.Transition
    public Animator createAnimator(final ViewGroup viewGroup, s sVar, s sVar2) {
        int i2;
        final View view;
        Animator animator;
        ObjectAnimator objectAnimator;
        int i3;
        Rect rect;
        ObjectAnimator objectAnimator2;
        if (sVar == null || sVar2 == null) {
            return null;
        }
        Map<String, Object> map = sVar.a;
        Map<String, Object> map2 = sVar2.a;
        ViewGroup viewGroup2 = (ViewGroup) map.get("android:changeBounds:parent");
        ViewGroup viewGroup3 = (ViewGroup) map2.get("android:changeBounds:parent");
        if (viewGroup2 == null || viewGroup3 == null) {
            return null;
        }
        final View view2 = sVar2.b;
        if (a(viewGroup2, viewGroup3)) {
            Rect rect2 = (Rect) sVar.a.get("android:changeBounds:bounds");
            Rect rect3 = (Rect) sVar2.a.get("android:changeBounds:bounds");
            int i4 = rect2.left;
            final int i5 = rect3.left;
            int i6 = rect2.top;
            final int i7 = rect3.top;
            int i8 = rect2.right;
            final int i9 = rect3.right;
            int i10 = rect2.bottom;
            final int i11 = rect3.bottom;
            int i12 = i8 - i4;
            int i13 = i10 - i6;
            int i14 = i9 - i5;
            int i15 = i11 - i7;
            Rect rect4 = (Rect) sVar.a.get("android:changeBounds:clip");
            final Rect rect5 = (Rect) sVar2.a.get("android:changeBounds:clip");
            if ((i12 == 0 || i13 == 0) && (i14 == 0 || i15 == 0)) {
                i2 = 0;
            } else {
                i2 = (i4 == i5 && i6 == i7) ? 0 : 1;
                if (!(i8 == i9 && i10 == i11)) {
                    i2++;
                }
            }
            if ((rect4 != null && !rect4.equals(rect5)) || (rect4 == null && rect5 != null)) {
                i2++;
            }
            if (i2 <= 0) {
                return null;
            }
            if (!this.i) {
                view = view2;
                ad.a(view, i4, i6, i8, i10);
                if (i2 == 2) {
                    if (i12 == i14 && i13 == i15) {
                        animator = f.a(view, g, getPathMotion().a((float) i4, (float) i6, (float) i5, (float) i7));
                    } else {
                        final a aVar = new a(view);
                        ObjectAnimator a2 = f.a(aVar, c, getPathMotion().a((float) i4, (float) i6, (float) i5, (float) i7));
                        ObjectAnimator a3 = f.a(aVar, d, getPathMotion().a((float) i8, (float) i10, (float) i9, (float) i11));
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(a2, a3);
                        animatorSet.addListener(new AnimatorListenerAdapter() {
                            /* class androidx.transition.c.AnonymousClass8 */
                            private a mViewBounds = aVar;
                        });
                        animator = animatorSet;
                    }
                } else if (i4 == i5 && i6 == i7) {
                    animator = f.a(view, e, getPathMotion().a((float) i8, (float) i10, (float) i9, (float) i11));
                } else {
                    animator = f.a(view, f, getPathMotion().a((float) i4, (float) i6, (float) i5, (float) i7));
                }
            } else {
                view = view2;
                ad.a(view, i4, i6, Math.max(i12, i14) + i4, Math.max(i13, i15) + i6);
                if (i4 == i5 && i6 == i7) {
                    objectAnimator = null;
                } else {
                    objectAnimator = f.a(view, g, getPathMotion().a((float) i4, (float) i6, (float) i5, (float) i7));
                }
                if (rect4 == null) {
                    i3 = 0;
                    rect = new Rect(0, 0, i12, i13);
                } else {
                    i3 = 0;
                    rect = rect4;
                }
                Rect rect6 = rect5 == null ? new Rect(i3, i3, i14, i15) : rect5;
                if (!rect.equals(rect6)) {
                    r.a(view, rect);
                    j jVar = k;
                    Object[] objArr = new Object[2];
                    objArr[i3] = rect;
                    objArr[1] = rect6;
                    objectAnimator2 = ObjectAnimator.ofObject(view, "clipBounds", jVar, objArr);
                    objectAnimator2.addListener(new AnimatorListenerAdapter() {
                        /* class androidx.transition.c.AnonymousClass9 */
                        private boolean h;

                        public void onAnimationCancel(Animator animator) {
                            this.h = true;
                        }

                        public void onAnimationEnd(Animator animator) {
                            if (!this.h) {
                                r.a(view, rect5);
                                ad.a(view, i5, i7, i9, i11);
                            }
                        }
                    });
                } else {
                    objectAnimator2 = null;
                }
                animator = q.a(objectAnimator, objectAnimator2);
            }
            if (view.getParent() instanceof ViewGroup) {
                final ViewGroup viewGroup4 = (ViewGroup) view.getParent();
                x.a(viewGroup4, true);
                addListener(new m() {
                    /* class androidx.transition.c.AnonymousClass10 */
                    boolean a = false;

                    @Override // androidx.transition.m, androidx.transition.Transition.d
                    public void a(Transition transition) {
                        x.a(viewGroup4, false);
                        this.a = true;
                    }

                    @Override // androidx.transition.m, androidx.transition.Transition.d
                    public void b(Transition transition) {
                        if (!this.a) {
                            x.a(viewGroup4, false);
                        }
                        transition.removeListener(this);
                    }

                    @Override // androidx.transition.m, androidx.transition.Transition.d
                    public void c(Transition transition) {
                        x.a(viewGroup4, false);
                    }

                    @Override // androidx.transition.m, androidx.transition.Transition.d
                    public void d(Transition transition) {
                        x.a(viewGroup4, true);
                    }
                });
            }
            return animator;
        }
        int intValue = ((Integer) sVar.a.get("android:changeBounds:windowX")).intValue();
        int intValue2 = ((Integer) sVar.a.get("android:changeBounds:windowY")).intValue();
        int intValue3 = ((Integer) sVar2.a.get("android:changeBounds:windowX")).intValue();
        int intValue4 = ((Integer) sVar2.a.get("android:changeBounds:windowY")).intValue();
        if (intValue == intValue3 && intValue2 == intValue4) {
            return null;
        }
        viewGroup.getLocationInWindow(this.h);
        Bitmap createBitmap = Bitmap.createBitmap(view2.getWidth(), view2.getHeight(), Bitmap.Config.ARGB_8888);
        view2.draw(new Canvas(createBitmap));
        final BitmapDrawable bitmapDrawable = new BitmapDrawable(createBitmap);
        final float c2 = ad.c(view2);
        ad.a(view2, 0.0f);
        ad.a(viewGroup).a(bitmapDrawable);
        g pathMotion = getPathMotion();
        int[] iArr = this.h;
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(bitmapDrawable, i.a(b, pathMotion.a((float) (intValue - iArr[0]), (float) (intValue2 - iArr[1]), (float) (intValue3 - iArr[0]), (float) (intValue4 - iArr[1]))));
        ofPropertyValuesHolder.addListener(new AnimatorListenerAdapter() {
            /* class androidx.transition.c.AnonymousClass2 */

            public void onAnimationEnd(Animator animator) {
                ad.a(viewGroup).b(bitmapDrawable);
                ad.a(view2, c2);
            }
        });
        return ofPropertyValuesHolder;
    }

    /* access modifiers changed from: private */
    public static class a {
        private int a;
        private int b;
        private int c;
        private int d;
        private View e;
        private int f;
        private int g;

        a(View view) {
            this.e = view;
        }

        /* access modifiers changed from: package-private */
        public void a(PointF pointF) {
            this.a = Math.round(pointF.x);
            this.b = Math.round(pointF.y);
            this.f++;
            if (this.f == this.g) {
                a();
            }
        }

        /* access modifiers changed from: package-private */
        public void b(PointF pointF) {
            this.c = Math.round(pointF.x);
            this.d = Math.round(pointF.y);
            this.g++;
            if (this.f == this.g) {
                a();
            }
        }

        private void a() {
            ad.a(this.e, this.a, this.b, this.c, this.d);
            this.f = 0;
            this.g = 0;
        }
    }
}
