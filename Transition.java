package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import androidx.core.content.a.g;
import androidx.core.f.r;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.xmlpull.v1.XmlPullParser;

public abstract class Transition implements Cloneable {
    static final boolean DBG = false;
    private static final int[] DEFAULT_MATCH_ORDER = {2, 1, 3, 4};
    private static final String LOG_TAG = "Transition";
    private static final int MATCH_FIRST = 1;
    public static final int MATCH_ID = 3;
    private static final String MATCH_ID_STR = "id";
    public static final int MATCH_INSTANCE = 1;
    private static final String MATCH_INSTANCE_STR = "instance";
    public static final int MATCH_ITEM_ID = 4;
    private static final String MATCH_ITEM_ID_STR = "itemId";
    private static final int MATCH_LAST = 4;
    public static final int MATCH_NAME = 2;
    private static final String MATCH_NAME_STR = "name";
    private static final g STRAIGHT_PATH_MOTION = new g() {
        /* class androidx.transition.Transition.AnonymousClass1 */

        @Override // androidx.transition.g
        public Path a(float f, float f2, float f3, float f4) {
            Path path = new Path();
            path.moveTo(f, f2);
            path.lineTo(f3, f4);
            return path;
        }
    };
    private static ThreadLocal<androidx.a.a<Animator, a>> sRunningAnimators = new ThreadLocal<>();
    private ArrayList<Animator> mAnimators = new ArrayList<>();
    boolean mCanRemoveViews = false;
    ArrayList<Animator> mCurrentAnimators = new ArrayList<>();
    long mDuration = -1;
    private t mEndValues = new t();
    private ArrayList<s> mEndValuesList;
    private boolean mEnded = false;
    private c mEpicenterCallback;
    private TimeInterpolator mInterpolator = null;
    private ArrayList<d> mListeners = null;
    private int[] mMatchOrder = DEFAULT_MATCH_ORDER;
    private String mName = getClass().getName();
    private androidx.a.a<String, String> mNameOverrides;
    private int mNumInstances = 0;
    p mParent = null;
    private g mPathMotion = STRAIGHT_PATH_MOTION;
    private boolean mPaused = false;
    o mPropagation;
    private ViewGroup mSceneRoot = null;
    private long mStartDelay = -1;
    private t mStartValues = new t();
    private ArrayList<s> mStartValuesList;
    private ArrayList<View> mTargetChildExcludes = null;
    private ArrayList<View> mTargetExcludes = null;
    private ArrayList<Integer> mTargetIdChildExcludes = null;
    private ArrayList<Integer> mTargetIdExcludes = null;
    ArrayList<Integer> mTargetIds = new ArrayList<>();
    private ArrayList<String> mTargetNameExcludes = null;
    private ArrayList<String> mTargetNames = null;
    private ArrayList<Class> mTargetTypeChildExcludes = null;
    private ArrayList<Class> mTargetTypeExcludes = null;
    private ArrayList<Class> mTargetTypes = null;
    ArrayList<View> mTargets = new ArrayList<>();

    public static abstract class c {
        public abstract Rect a(Transition transition);
    }

    public interface d {
        void a(Transition transition);

        void b(Transition transition);

        void c(Transition transition);

        void d(Transition transition);

        void e(Transition transition);
    }

    private static boolean isValidMatch(int i) {
        return i >= 1 && i <= 4;
    }

    public abstract void captureEndValues(s sVar);

    public abstract void captureStartValues(s sVar);

    public Animator createAnimator(ViewGroup viewGroup, s sVar, s sVar2) {
        return null;
    }

    public String[] getTransitionProperties() {
        return null;
    }

    public Transition() {
    }

    public Transition(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, l.c);
        XmlResourceParser xmlResourceParser = (XmlResourceParser) attributeSet;
        long a2 = (long) g.a(obtainStyledAttributes, (XmlPullParser) xmlResourceParser, "duration", 1, -1);
        if (a2 >= 0) {
            setDuration(a2);
        }
        long a3 = (long) g.a(obtainStyledAttributes, (XmlPullParser) xmlResourceParser, "startDelay", 2, -1);
        if (a3 > 0) {
            setStartDelay(a3);
        }
        int c2 = g.c(obtainStyledAttributes, xmlResourceParser, "interpolator", 0, 0);
        if (c2 > 0) {
            setInterpolator(AnimationUtils.loadInterpolator(context, c2));
        }
        String a4 = g.a(obtainStyledAttributes, xmlResourceParser, "matchOrder", 3);
        if (a4 != null) {
            setMatchOrder(parseMatchOrder(a4));
        }
        obtainStyledAttributes.recycle();
    }

    private static int[] parseMatchOrder(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
        int[] iArr = new int[stringTokenizer.countTokens()];
        int i = 0;
        while (stringTokenizer.hasMoreTokens()) {
            String trim = stringTokenizer.nextToken().trim();
            if (MATCH_ID_STR.equalsIgnoreCase(trim)) {
                iArr[i] = 3;
            } else if (MATCH_INSTANCE_STR.equalsIgnoreCase(trim)) {
                iArr[i] = 1;
            } else if ("name".equalsIgnoreCase(trim)) {
                iArr[i] = 2;
            } else if (MATCH_ITEM_ID_STR.equalsIgnoreCase(trim)) {
                iArr[i] = 4;
            } else if (trim.isEmpty()) {
                int[] iArr2 = new int[(iArr.length - 1)];
                System.arraycopy(iArr, 0, iArr2, 0, i);
                i--;
                iArr = iArr2;
            } else {
                throw new InflateException("Unknown match type in matchOrder: '" + trim + "'");
            }
            i++;
        }
        return iArr;
    }

    public Transition setDuration(long j) {
        this.mDuration = j;
        return this;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public Transition setStartDelay(long j) {
        this.mStartDelay = j;
        return this;
    }

    public long getStartDelay() {
        return this.mStartDelay;
    }

    public Transition setInterpolator(TimeInterpolator timeInterpolator) {
        this.mInterpolator = timeInterpolator;
        return this;
    }

    public TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    public void setMatchOrder(int... iArr) {
        if (iArr == null || iArr.length == 0) {
            this.mMatchOrder = DEFAULT_MATCH_ORDER;
            return;
        }
        for (int i = 0; i < iArr.length; i++) {
            if (!isValidMatch(iArr[i])) {
                throw new IllegalArgumentException("matches contains invalid value");
            } else if (alreadyContains(iArr, i)) {
                throw new IllegalArgumentException("matches contains a duplicate value");
            }
        }
        this.mMatchOrder = (int[]) iArr.clone();
    }

    private static boolean alreadyContains(int[] iArr, int i) {
        int i2 = iArr[i];
        for (int i3 = 0; i3 < i; i3++) {
            if (iArr[i3] == i2) {
                return true;
            }
        }
        return false;
    }

    private void matchInstances(androidx.a.a<View, s> aVar, androidx.a.a<View, s> aVar2) {
        s remove;
        for (int size = aVar.size() - 1; size >= 0; size--) {
            View b2 = aVar.b(size);
            if (!(b2 == null || !isValidTarget(b2) || (remove = aVar2.remove(b2)) == null || remove.b == null || !isValidTarget(remove.b))) {
                this.mStartValuesList.add(aVar.d(size));
                this.mEndValuesList.add(remove);
            }
        }
    }

    private void matchItemIds(androidx.a.a<View, s> aVar, androidx.a.a<View, s> aVar2, androidx.a.d<View> dVar, androidx.a.d<View> dVar2) {
        View a2;
        int b2 = dVar.b();
        for (int i = 0; i < b2; i++) {
            View c2 = dVar.c(i);
            if (c2 != null && isValidTarget(c2) && (a2 = dVar2.a(dVar.b(i))) != null && isValidTarget(a2)) {
                s sVar = aVar.get(c2);
                s sVar2 = aVar2.get(a2);
                if (!(sVar == null || sVar2 == null)) {
                    this.mStartValuesList.add(sVar);
                    this.mEndValuesList.add(sVar2);
                    aVar.remove(c2);
                    aVar2.remove(a2);
                }
            }
        }
    }

    private void matchIds(androidx.a.a<View, s> aVar, androidx.a.a<View, s> aVar2, SparseArray<View> sparseArray, SparseArray<View> sparseArray2) {
        View view;
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            View valueAt = sparseArray.valueAt(i);
            if (valueAt != null && isValidTarget(valueAt) && (view = sparseArray2.get(sparseArray.keyAt(i))) != null && isValidTarget(view)) {
                s sVar = aVar.get(valueAt);
                s sVar2 = aVar2.get(view);
                if (!(sVar == null || sVar2 == null)) {
                    this.mStartValuesList.add(sVar);
                    this.mEndValuesList.add(sVar2);
                    aVar.remove(valueAt);
                    aVar2.remove(view);
                }
            }
        }
    }

    private void matchNames(androidx.a.a<View, s> aVar, androidx.a.a<View, s> aVar2, androidx.a.a<String, View> aVar3, androidx.a.a<String, View> aVar4) {
        View view;
        int size = aVar3.size();
        for (int i = 0; i < size; i++) {
            View c2 = aVar3.c(i);
            if (c2 != null && isValidTarget(c2) && (view = aVar4.get(aVar3.b(i))) != null && isValidTarget(view)) {
                s sVar = aVar.get(c2);
                s sVar2 = aVar2.get(view);
                if (!(sVar == null || sVar2 == null)) {
                    this.mStartValuesList.add(sVar);
                    this.mEndValuesList.add(sVar2);
                    aVar.remove(c2);
                    aVar2.remove(view);
                }
            }
        }
    }

    private void addUnmatched(androidx.a.a<View, s> aVar, androidx.a.a<View, s> aVar2) {
        for (int i = 0; i < aVar.size(); i++) {
            s c2 = aVar.c(i);
            if (isValidTarget(c2.b)) {
                this.mStartValuesList.add(c2);
                this.mEndValuesList.add(null);
            }
        }
        for (int i2 = 0; i2 < aVar2.size(); i2++) {
            s c3 = aVar2.c(i2);
            if (isValidTarget(c3.b)) {
                this.mEndValuesList.add(c3);
                this.mStartValuesList.add(null);
            }
        }
    }

    private void matchStartAndEnd(t tVar, t tVar2) {
        androidx.a.a<View, s> aVar = new androidx.a.a<>(tVar.a);
        androidx.a.a<View, s> aVar2 = new androidx.a.a<>(tVar2.a);
        int i = 0;
        while (true) {
            int[] iArr = this.mMatchOrder;
            if (i < iArr.length) {
                switch (iArr[i]) {
                    case 1:
                        matchInstances(aVar, aVar2);
                        break;
                    case 2:
                        matchNames(aVar, aVar2, tVar.d, tVar2.d);
                        break;
                    case 3:
                        matchIds(aVar, aVar2, tVar.b, tVar2.b);
                        break;
                    case 4:
                        matchItemIds(aVar, aVar2, tVar.c, tVar2.c);
                        break;
                }
                i++;
            } else {
                addUnmatched(aVar, aVar2);
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void createAnimators(ViewGroup viewGroup, t tVar, t tVar2, ArrayList<s> arrayList, ArrayList<s> arrayList2) {
        int i;
        int i2;
        View view;
        Animator animator;
        s sVar;
        long j;
        s sVar2;
        Animator animator2;
        androidx.a.a<Animator, a> runningAnimators = getRunningAnimators();
        SparseIntArray sparseIntArray = new SparseIntArray();
        int size = arrayList.size();
        long j2 = Long.MAX_VALUE;
        int i3 = 0;
        while (i3 < size) {
            s sVar3 = arrayList.get(i3);
            s sVar4 = arrayList2.get(i3);
            if (sVar3 != null && !sVar3.c.contains(this)) {
                sVar3 = null;
            }
            if (sVar4 != null && !sVar4.c.contains(this)) {
                sVar4 = null;
            }
            if (sVar3 == null && sVar4 == null) {
                i2 = size;
                i = i3;
            } else if (sVar3 == null || sVar4 == null || isTransitionRequired(sVar3, sVar4)) {
                Animator createAnimator = createAnimator(viewGroup, sVar3, sVar4);
                if (createAnimator != null) {
                    if (sVar4 != null) {
                        view = sVar4.b;
                        String[] transitionProperties = getTransitionProperties();
                        if (view == null || transitionProperties == null || transitionProperties.length <= 0) {
                            i2 = size;
                            i = i3;
                            animator2 = createAnimator;
                            sVar2 = null;
                        } else {
                            sVar2 = new s();
                            sVar2.b = view;
                            i2 = size;
                            s sVar5 = tVar2.a.get(view);
                            if (sVar5 != null) {
                                int i4 = 0;
                                while (i4 < transitionProperties.length) {
                                    sVar2.a.put(transitionProperties[i4], sVar5.a.get(transitionProperties[i4]));
                                    i4++;
                                    i3 = i3;
                                    sVar5 = sVar5;
                                }
                                i = i3;
                            } else {
                                i = i3;
                            }
                            int size2 = runningAnimators.size();
                            int i5 = 0;
                            while (true) {
                                if (i5 >= size2) {
                                    animator2 = createAnimator;
                                    break;
                                }
                                a aVar = runningAnimators.get(runningAnimators.b(i5));
                                if (aVar.c != null && aVar.a == view && aVar.b.equals(getName()) && aVar.c.equals(sVar2)) {
                                    animator2 = null;
                                    break;
                                }
                                i5++;
                            }
                        }
                        animator = animator2;
                        sVar = sVar2;
                    } else {
                        i2 = size;
                        i = i3;
                        view = sVar3.b;
                        animator = createAnimator;
                        sVar = null;
                    }
                    if (animator != null) {
                        o oVar = this.mPropagation;
                        if (oVar != null) {
                            long a2 = oVar.a(viewGroup, this, sVar3, sVar4);
                            sparseIntArray.put(this.mAnimators.size(), (int) a2);
                            j = Math.min(a2, j2);
                        } else {
                            j = j2;
                        }
                        runningAnimators.put(animator, new a(view, getName(), this, ad.b(viewGroup), sVar));
                        this.mAnimators.add(animator);
                        j2 = j;
                    }
                } else {
                    i2 = size;
                    i = i3;
                }
            } else {
                i2 = size;
                i = i3;
            }
            i3 = i + 1;
            size = i2;
        }
        if (j2 != 0) {
            for (int i6 = 0; i6 < sparseIntArray.size(); i6++) {
                Animator animator3 = this.mAnimators.get(sparseIntArray.keyAt(i6));
                animator3.setStartDelay((((long) sparseIntArray.valueAt(i6)) - j2) + animator3.getStartDelay());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isValidTarget(View view) {
        ArrayList<Class> arrayList;
        ArrayList<String> arrayList2;
        int id = view.getId();
        ArrayList<Integer> arrayList3 = this.mTargetIdExcludes;
        if (arrayList3 != null && arrayList3.contains(Integer.valueOf(id))) {
            return false;
        }
        ArrayList<View> arrayList4 = this.mTargetExcludes;
        if (arrayList4 != null && arrayList4.contains(view)) {
            return false;
        }
        ArrayList<Class> arrayList5 = this.mTargetTypeExcludes;
        if (arrayList5 != null) {
            int size = arrayList5.size();
            for (int i = 0; i < size; i++) {
                if (this.mTargetTypeExcludes.get(i).isInstance(view)) {
                    return false;
                }
            }
        }
        if (!(this.mTargetNameExcludes == null || r.n(view) == null || !this.mTargetNameExcludes.contains(r.n(view)))) {
            return false;
        }
        if ((this.mTargetIds.size() == 0 && this.mTargets.size() == 0 && (((arrayList = this.mTargetTypes) == null || arrayList.isEmpty()) && ((arrayList2 = this.mTargetNames) == null || arrayList2.isEmpty()))) || this.mTargetIds.contains(Integer.valueOf(id)) || this.mTargets.contains(view)) {
            return true;
        }
        ArrayList<String> arrayList6 = this.mTargetNames;
        if (arrayList6 != null && arrayList6.contains(r.n(view))) {
            return true;
        }
        if (this.mTargetTypes != null) {
            for (int i2 = 0; i2 < this.mTargetTypes.size(); i2++) {
                if (this.mTargetTypes.get(i2).isInstance(view)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static androidx.a.a<Animator, a> getRunningAnimators() {
        androidx.a.a<Animator, a> aVar = sRunningAnimators.get();
        if (aVar != null) {
            return aVar;
        }
        androidx.a.a<Animator, a> aVar2 = new androidx.a.a<>();
        sRunningAnimators.set(aVar2);
        return aVar2;
    }

    /* access modifiers changed from: protected */
    public void runAnimators() {
        start();
        androidx.a.a<Animator, a> runningAnimators = getRunningAnimators();
        Iterator<Animator> it = this.mAnimators.iterator();
        while (it.hasNext()) {
            Animator next = it.next();
            if (runningAnimators.containsKey(next)) {
                start();
                runAnimator(next, runningAnimators);
            }
        }
        this.mAnimators.clear();
        end();
    }

    private void runAnimator(Animator animator, final androidx.a.a<Animator, a> aVar) {
        if (animator != null) {
            animator.addListener(new AnimatorListenerAdapter() {
                /* class androidx.transition.Transition.AnonymousClass2 */

                public void onAnimationStart(Animator animator) {
                    Transition.this.mCurrentAnimators.add(animator);
                }

                public void onAnimationEnd(Animator animator) {
                    aVar.remove(animator);
                    Transition.this.mCurrentAnimators.remove(animator);
                }
            });
            animate(animator);
        }
    }

    public Transition addTarget(View view) {
        this.mTargets.add(view);
        return this;
    }

    public Transition addTarget(int i) {
        if (i != 0) {
            this.mTargetIds.add(Integer.valueOf(i));
        }
        return this;
    }

    public Transition addTarget(String str) {
        if (this.mTargetNames == null) {
            this.mTargetNames = new ArrayList<>();
        }
        this.mTargetNames.add(str);
        return this;
    }

    public Transition addTarget(Class cls) {
        if (this.mTargetTypes == null) {
            this.mTargetTypes = new ArrayList<>();
        }
        this.mTargetTypes.add(cls);
        return this;
    }

    public Transition removeTarget(View view) {
        this.mTargets.remove(view);
        return this;
    }

    public Transition removeTarget(int i) {
        if (i != 0) {
            this.mTargetIds.remove(Integer.valueOf(i));
        }
        return this;
    }

    public Transition removeTarget(String str) {
        ArrayList<String> arrayList = this.mTargetNames;
        if (arrayList != null) {
            arrayList.remove(str);
        }
        return this;
    }

    public Transition removeTarget(Class cls) {
        ArrayList<Class> arrayList = this.mTargetTypes;
        if (arrayList != null) {
            arrayList.remove(cls);
        }
        return this;
    }

    private static <T> ArrayList<T> excludeObject(ArrayList<T> arrayList, T t, boolean z) {
        if (t == null) {
            return arrayList;
        }
        if (z) {
            return b.a(arrayList, t);
        }
        return b.b(arrayList, t);
    }

    public Transition excludeTarget(View view, boolean z) {
        this.mTargetExcludes = excludeView(this.mTargetExcludes, view, z);
        return this;
    }

    public Transition excludeTarget(int i, boolean z) {
        this.mTargetIdExcludes = excludeId(this.mTargetIdExcludes, i, z);
        return this;
    }

    public Transition excludeTarget(String str, boolean z) {
        this.mTargetNameExcludes = excludeObject(this.mTargetNameExcludes, str, z);
        return this;
    }

    public Transition excludeChildren(View view, boolean z) {
        this.mTargetChildExcludes = excludeView(this.mTargetChildExcludes, view, z);
        return this;
    }

    public Transition excludeChildren(int i, boolean z) {
        this.mTargetIdChildExcludes = excludeId(this.mTargetIdChildExcludes, i, z);
        return this;
    }

    private ArrayList<Integer> excludeId(ArrayList<Integer> arrayList, int i, boolean z) {
        if (i <= 0) {
            return arrayList;
        }
        if (z) {
            return b.a(arrayList, Integer.valueOf(i));
        }
        return b.b(arrayList, Integer.valueOf(i));
    }

    private ArrayList<View> excludeView(ArrayList<View> arrayList, View view, boolean z) {
        if (view == null) {
            return arrayList;
        }
        if (z) {
            return b.a(arrayList, view);
        }
        return b.b(arrayList, view);
    }

    public Transition excludeTarget(Class cls, boolean z) {
        this.mTargetTypeExcludes = excludeType(this.mTargetTypeExcludes, cls, z);
        return this;
    }

    public Transition excludeChildren(Class cls, boolean z) {
        this.mTargetTypeChildExcludes = excludeType(this.mTargetTypeChildExcludes, cls, z);
        return this;
    }

    private ArrayList<Class> excludeType(ArrayList<Class> arrayList, Class cls, boolean z) {
        if (cls == null) {
            return arrayList;
        }
        if (z) {
            return b.a(arrayList, cls);
        }
        return b.b(arrayList, cls);
    }

    public List<Integer> getTargetIds() {
        return this.mTargetIds;
    }

    public List<View> getTargets() {
        return this.mTargets;
    }

    public List<String> getTargetNames() {
        return this.mTargetNames;
    }

    public List<Class> getTargetTypes() {
        return this.mTargetTypes;
    }

    /* access modifiers changed from: package-private */
    public void captureValues(ViewGroup viewGroup, boolean z) {
        androidx.a.a<String, String> aVar;
        ArrayList<String> arrayList;
        ArrayList<Class> arrayList2;
        clearValues(z);
        if ((this.mTargetIds.size() > 0 || this.mTargets.size() > 0) && (((arrayList = this.mTargetNames) == null || arrayList.isEmpty()) && ((arrayList2 = this.mTargetTypes) == null || arrayList2.isEmpty()))) {
            for (int i = 0; i < this.mTargetIds.size(); i++) {
                View findViewById = viewGroup.findViewById(this.mTargetIds.get(i).intValue());
                if (findViewById != null) {
                    s sVar = new s();
                    sVar.b = findViewById;
                    if (z) {
                        captureStartValues(sVar);
                    } else {
                        captureEndValues(sVar);
                    }
                    sVar.c.add(this);
                    capturePropagationValues(sVar);
                    if (z) {
                        addViewValues(this.mStartValues, findViewById, sVar);
                    } else {
                        addViewValues(this.mEndValues, findViewById, sVar);
                    }
                }
            }
            for (int i2 = 0; i2 < this.mTargets.size(); i2++) {
                View view = this.mTargets.get(i2);
                s sVar2 = new s();
                sVar2.b = view;
                if (z) {
                    captureStartValues(sVar2);
                } else {
                    captureEndValues(sVar2);
                }
                sVar2.c.add(this);
                capturePropagationValues(sVar2);
                if (z) {
                    addViewValues(this.mStartValues, view, sVar2);
                } else {
                    addViewValues(this.mEndValues, view, sVar2);
                }
            }
        } else {
            captureHierarchy(viewGroup, z);
        }
        if (!(z || (aVar = this.mNameOverrides) == null)) {
            int size = aVar.size();
            ArrayList arrayList3 = new ArrayList(size);
            for (int i3 = 0; i3 < size; i3++) {
                arrayList3.add(this.mStartValues.d.remove(this.mNameOverrides.b(i3)));
            }
            for (int i4 = 0; i4 < size; i4++) {
                View view2 = (View) arrayList3.get(i4);
                if (view2 != null) {
                    this.mStartValues.d.put(this.mNameOverrides.c(i4), view2);
                }
            }
        }
    }

    private static void addViewValues(t tVar, View view, s sVar) {
        tVar.a.put(view, sVar);
        int id = view.getId();
        if (id >= 0) {
            if (tVar.b.indexOfKey(id) >= 0) {
                tVar.b.put(id, null);
            } else {
                tVar.b.put(id, view);
            }
        }
        String n = r.n(view);
        if (n != null) {
            if (tVar.d.containsKey(n)) {
                tVar.d.put(n, null);
            } else {
                tVar.d.put(n, view);
            }
        }
        if (view.getParent() instanceof ListView) {
            ListView listView = (ListView) view.getParent();
            if (listView.getAdapter().hasStableIds()) {
                long itemIdAtPosition = listView.getItemIdAtPosition(listView.getPositionForView(view));
                if (tVar.c.c(itemIdAtPosition) >= 0) {
                    View a2 = tVar.c.a(itemIdAtPosition);
                    if (a2 != null) {
                        r.a(a2, false);
                        tVar.c.b(itemIdAtPosition, null);
                        return;
                    }
                    return;
                }
                r.a(view, true);
                tVar.c.b(itemIdAtPosition, view);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void clearValues(boolean z) {
        if (z) {
            this.mStartValues.a.clear();
            this.mStartValues.b.clear();
            this.mStartValues.c.c();
            return;
        }
        this.mEndValues.a.clear();
        this.mEndValues.b.clear();
        this.mEndValues.c.c();
    }

    private void captureHierarchy(View view, boolean z) {
        if (view != null) {
            int id = view.getId();
            ArrayList<Integer> arrayList = this.mTargetIdExcludes;
            if (arrayList == null || !arrayList.contains(Integer.valueOf(id))) {
                ArrayList<View> arrayList2 = this.mTargetExcludes;
                if (arrayList2 == null || !arrayList2.contains(view)) {
                    ArrayList<Class> arrayList3 = this.mTargetTypeExcludes;
                    if (arrayList3 != null) {
                        int size = arrayList3.size();
                        for (int i = 0; i < size; i++) {
                            if (this.mTargetTypeExcludes.get(i).isInstance(view)) {
                                return;
                            }
                        }
                    }
                    if (view.getParent() instanceof ViewGroup) {
                        s sVar = new s();
                        sVar.b = view;
                        if (z) {
                            captureStartValues(sVar);
                        } else {
                            captureEndValues(sVar);
                        }
                        sVar.c.add(this);
                        capturePropagationValues(sVar);
                        if (z) {
                            addViewValues(this.mStartValues, view, sVar);
                        } else {
                            addViewValues(this.mEndValues, view, sVar);
                        }
                    }
                    if (view instanceof ViewGroup) {
                        ArrayList<Integer> arrayList4 = this.mTargetIdChildExcludes;
                        if (arrayList4 == null || !arrayList4.contains(Integer.valueOf(id))) {
                            ArrayList<View> arrayList5 = this.mTargetChildExcludes;
                            if (arrayList5 == null || !arrayList5.contains(view)) {
                                ArrayList<Class> arrayList6 = this.mTargetTypeChildExcludes;
                                if (arrayList6 != null) {
                                    int size2 = arrayList6.size();
                                    for (int i2 = 0; i2 < size2; i2++) {
                                        if (this.mTargetTypeChildExcludes.get(i2).isInstance(view)) {
                                            return;
                                        }
                                    }
                                }
                                ViewGroup viewGroup = (ViewGroup) view;
                                for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
                                    captureHierarchy(viewGroup.getChildAt(i3), z);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public s getTransitionValues(View view, boolean z) {
        p pVar = this.mParent;
        if (pVar != null) {
            return pVar.getTransitionValues(view, z);
        }
        return (z ? this.mStartValues : this.mEndValues).a.get(view);
    }

    /* access modifiers changed from: package-private */
    public s getMatchedTransitionValues(View view, boolean z) {
        p pVar = this.mParent;
        if (pVar != null) {
            return pVar.getMatchedTransitionValues(view, z);
        }
        ArrayList<s> arrayList = z ? this.mStartValuesList : this.mEndValuesList;
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        int i = -1;
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                break;
            }
            s sVar = arrayList.get(i2);
            if (sVar == null) {
                return null;
            }
            if (sVar.b == view) {
                i = i2;
                break;
            }
            i2++;
        }
        if (i < 0) {
            return null;
        }
        return (z ? this.mEndValuesList : this.mStartValuesList).get(i);
    }

    public void pause(View view) {
        if (!this.mEnded) {
            androidx.a.a<Animator, a> runningAnimators = getRunningAnimators();
            int size = runningAnimators.size();
            al b2 = ad.b(view);
            for (int i = size - 1; i >= 0; i--) {
                a c2 = runningAnimators.c(i);
                if (c2.a != null && b2.equals(c2.d)) {
                    a.a(runningAnimators.b(i));
                }
            }
            ArrayList<d> arrayList = this.mListeners;
            if (arrayList != null && arrayList.size() > 0) {
                ArrayList arrayList2 = (ArrayList) this.mListeners.clone();
                int size2 = arrayList2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    ((d) arrayList2.get(i2)).c(this);
                }
            }
            this.mPaused = true;
        }
    }

    public void resume(View view) {
        if (this.mPaused) {
            if (!this.mEnded) {
                androidx.a.a<Animator, a> runningAnimators = getRunningAnimators();
                int size = runningAnimators.size();
                al b2 = ad.b(view);
                for (int i = size - 1; i >= 0; i--) {
                    a c2 = runningAnimators.c(i);
                    if (c2.a != null && b2.equals(c2.d)) {
                        a.b(runningAnimators.b(i));
                    }
                }
                ArrayList<d> arrayList = this.mListeners;
                if (arrayList != null && arrayList.size() > 0) {
                    ArrayList arrayList2 = (ArrayList) this.mListeners.clone();
                    int size2 = arrayList2.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        ((d) arrayList2.get(i2)).d(this);
                    }
                }
            }
            this.mPaused = false;
        }
    }

    /* access modifiers changed from: package-private */
    public void playTransition(ViewGroup viewGroup) {
        a aVar;
        this.mStartValuesList = new ArrayList<>();
        this.mEndValuesList = new ArrayList<>();
        matchStartAndEnd(this.mStartValues, this.mEndValues);
        androidx.a.a<Animator, a> runningAnimators = getRunningAnimators();
        int size = runningAnimators.size();
        al b2 = ad.b(viewGroup);
        for (int i = size - 1; i >= 0; i--) {
            Animator b3 = runningAnimators.b(i);
            if (!(b3 == null || (aVar = runningAnimators.get(b3)) == null || aVar.a == null || !b2.equals(aVar.d))) {
                s sVar = aVar.c;
                View view = aVar.a;
                s transitionValues = getTransitionValues(view, true);
                s matchedTransitionValues = getMatchedTransitionValues(view, true);
                if (!(transitionValues == null && matchedTransitionValues == null) && aVar.e.isTransitionRequired(sVar, matchedTransitionValues)) {
                    if (b3.isRunning() || b3.isStarted()) {
                        b3.cancel();
                    } else {
                        runningAnimators.remove(b3);
                    }
                }
            }
        }
        createAnimators(viewGroup, this.mStartValues, this.mEndValues, this.mStartValuesList, this.mEndValuesList);
        runAnimators();
    }

    public boolean isTransitionRequired(s sVar, s sVar2) {
        if (sVar == null || sVar2 == null) {
            return false;
        }
        String[] transitionProperties = getTransitionProperties();
        if (transitionProperties != null) {
            for (String str : transitionProperties) {
                if (isValueChanged(sVar, sVar2, str)) {
                    return true;
                }
            }
            return false;
        }
        for (String str2 : sVar.a.keySet()) {
            if (isValueChanged(sVar, sVar2, str2)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValueChanged(s sVar, s sVar2, String str) {
        Object obj = sVar.a.get(str);
        Object obj2 = sVar2.a.get(str);
        if (obj == null && obj2 == null) {
            return false;
        }
        if (obj == null || obj2 == null) {
            return true;
        }
        return true ^ obj.equals(obj2);
    }

    /* access modifiers changed from: protected */
    public void animate(Animator animator) {
        if (animator == null) {
            end();
            return;
        }
        if (getDuration() >= 0) {
            animator.setDuration(getDuration());
        }
        if (getStartDelay() >= 0) {
            animator.setStartDelay(getStartDelay());
        }
        if (getInterpolator() != null) {
            animator.setInterpolator(getInterpolator());
        }
        animator.addListener(new AnimatorListenerAdapter() {
            /* class androidx.transition.Transition.AnonymousClass3 */

            public void onAnimationEnd(Animator animator) {
                Transition.this.end();
                animator.removeListener(this);
            }
        });
        animator.start();
    }

    /* access modifiers changed from: protected */
    public void start() {
        if (this.mNumInstances == 0) {
            ArrayList<d> arrayList = this.mListeners;
            if (arrayList != null && arrayList.size() > 0) {
                ArrayList arrayList2 = (ArrayList) this.mListeners.clone();
                int size = arrayList2.size();
                for (int i = 0; i < size; i++) {
                    ((d) arrayList2.get(i)).e(this);
                }
            }
            this.mEnded = false;
        }
        this.mNumInstances++;
    }

    /* access modifiers changed from: protected */
    public void end() {
        this.mNumInstances--;
        if (this.mNumInstances == 0) {
            ArrayList<d> arrayList = this.mListeners;
            if (arrayList != null && arrayList.size() > 0) {
                ArrayList arrayList2 = (ArrayList) this.mListeners.clone();
                int size = arrayList2.size();
                for (int i = 0; i < size; i++) {
                    ((d) arrayList2.get(i)).b(this);
                }
            }
            for (int i2 = 0; i2 < this.mStartValues.c.b(); i2++) {
                View c2 = this.mStartValues.c.c(i2);
                if (c2 != null) {
                    r.a(c2, false);
                }
            }
            for (int i3 = 0; i3 < this.mEndValues.c.b(); i3++) {
                View c3 = this.mEndValues.c.c(i3);
                if (c3 != null) {
                    r.a(c3, false);
                }
            }
            this.mEnded = true;
        }
    }

    /* access modifiers changed from: package-private */
    public void forceToEnd(ViewGroup viewGroup) {
        androidx.a.a<Animator, a> runningAnimators = getRunningAnimators();
        int size = runningAnimators.size();
        if (viewGroup != null) {
            al b2 = ad.b(viewGroup);
            for (int i = size - 1; i >= 0; i--) {
                a c2 = runningAnimators.c(i);
                if (!(c2.a == null || b2 == null || !b2.equals(c2.d))) {
                    runningAnimators.b(i).end();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void cancel() {
        for (int size = this.mCurrentAnimators.size() - 1; size >= 0; size--) {
            this.mCurrentAnimators.get(size).cancel();
        }
        ArrayList<d> arrayList = this.mListeners;
        if (arrayList != null && arrayList.size() > 0) {
            ArrayList arrayList2 = (ArrayList) this.mListeners.clone();
            int size2 = arrayList2.size();
            for (int i = 0; i < size2; i++) {
                ((d) arrayList2.get(i)).a(this);
            }
        }
    }

    public Transition addListener(d dVar) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        }
        this.mListeners.add(dVar);
        return this;
    }

    public Transition removeListener(d dVar) {
        ArrayList<d> arrayList = this.mListeners;
        if (arrayList == null) {
            return this;
        }
        arrayList.remove(dVar);
        if (this.mListeners.size() == 0) {
            this.mListeners = null;
        }
        return this;
    }

    public void setPathMotion(g gVar) {
        if (gVar == null) {
            this.mPathMotion = STRAIGHT_PATH_MOTION;
        } else {
            this.mPathMotion = gVar;
        }
    }

    public g getPathMotion() {
        return this.mPathMotion;
    }

    public void setEpicenterCallback(c cVar) {
        this.mEpicenterCallback = cVar;
    }

    public c getEpicenterCallback() {
        return this.mEpicenterCallback;
    }

    public Rect getEpicenter() {
        c cVar = this.mEpicenterCallback;
        if (cVar == null) {
            return null;
        }
        return cVar.a(this);
    }

    public void setPropagation(o oVar) {
        this.mPropagation = oVar;
    }

    public o getPropagation() {
        return this.mPropagation;
    }

    /* access modifiers changed from: package-private */
    public void capturePropagationValues(s sVar) {
        String[] a2;
        if (this.mPropagation != null && !sVar.a.isEmpty() && (a2 = this.mPropagation.a()) != null) {
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= a2.length) {
                    z = true;
                    break;
                } else if (!sVar.a.containsKey(a2[i])) {
                    break;
                } else {
                    i++;
                }
            }
            if (!z) {
                this.mPropagation.a(sVar);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Transition setSceneRoot(ViewGroup viewGroup) {
        this.mSceneRoot = viewGroup;
        return this;
    }

    /* access modifiers changed from: package-private */
    public void setCanRemoveViews(boolean z) {
        this.mCanRemoveViews = z;
    }

    public String toString() {
        return toString("");
    }

    @Override // java.lang.Object
    public Transition clone() {
        try {
            Transition transition = (Transition) super.clone();
            transition.mAnimators = new ArrayList<>();
            transition.mStartValues = new t();
            transition.mEndValues = new t();
            transition.mStartValuesList = null;
            transition.mEndValuesList = null;
            return transition;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    public String getName() {
        return this.mName;
    }

    /* access modifiers changed from: package-private */
    public String toString(String str) {
        String str2 = str + getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + ": ";
        if (this.mDuration != -1) {
            str2 = str2 + "dur(" + this.mDuration + ") ";
        }
        if (this.mStartDelay != -1) {
            str2 = str2 + "dly(" + this.mStartDelay + ") ";
        }
        if (this.mInterpolator != null) {
            str2 = str2 + "interp(" + this.mInterpolator + ") ";
        }
        if (this.mTargetIds.size() <= 0 && this.mTargets.size() <= 0) {
            return str2;
        }
        String str3 = str2 + "tgts(";
        if (this.mTargetIds.size() > 0) {
            String str4 = str3;
            for (int i = 0; i < this.mTargetIds.size(); i++) {
                if (i > 0) {
                    str4 = str4 + ", ";
                }
                str4 = str4 + this.mTargetIds.get(i);
            }
            str3 = str4;
        }
        if (this.mTargets.size() > 0) {
            for (int i2 = 0; i2 < this.mTargets.size(); i2++) {
                if (i2 > 0) {
                    str3 = str3 + ", ";
                }
                str3 = str3 + this.mTargets.get(i2);
            }
        }
        return str3 + ")";
    }

    /* access modifiers changed from: private */
    public static class a {
        View a;
        String b;
        s c;
        al d;
        Transition e;

        a(View view, String str, Transition transition, al alVar, s sVar) {
            this.a = view;
            this.b = str;
            this.c = sVar;
            this.d = alVar;
            this.e = transition;
        }
    }

    /* access modifiers changed from: private */
    public static class b {
        static <T> ArrayList<T> a(ArrayList<T> arrayList, T t) {
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
            if (!arrayList.contains(t)) {
                arrayList.add(t);
            }
            return arrayList;
        }

        static <T> ArrayList<T> b(ArrayList<T> arrayList, T t) {
            if (arrayList == null) {
                return arrayList;
            }
            arrayList.remove(t);
            if (arrayList.isEmpty()) {
                return null;
            }
            return arrayList;
        }
    }
}
