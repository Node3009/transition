package androidx.transition;

import android.animation.TimeInterpolator;
import android.util.AndroidRuntimeException;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.Transition;
import java.util.ArrayList;
import java.util.Iterator;

public class p extends Transition {
    int a;
    boolean b = false;
    private ArrayList<Transition> c = new ArrayList<>();
    private boolean d = true;
    private int e = 0;

    public p a(int i) {
        switch (i) {
            case 0:
                this.d = true;
                break;
            case 1:
                this.d = false;
                break;
            default:
                throw new AndroidRuntimeException("Invalid parameter for TransitionSet ordering: " + i);
        }
        return this;
    }

    public p a(Transition transition) {
        this.c.add(transition);
        transition.mParent = this;
        if (this.mDuration >= 0) {
            transition.setDuration(this.mDuration);
        }
        if ((this.e & 1) != 0) {
            transition.setInterpolator(getInterpolator());
        }
        if ((this.e & 2) != 0) {
            transition.setPropagation(getPropagation());
        }
        if ((this.e & 4) != 0) {
            transition.setPathMotion(getPathMotion());
        }
        if ((this.e & 8) != 0) {
            transition.setEpicenterCallback(getEpicenterCallback());
        }
        return this;
    }

    public int a() {
        return this.c.size();
    }

    public Transition b(int i) {
        if (i < 0 || i >= this.c.size()) {
            return null;
        }
        return this.c.get(i);
    }

    /* renamed from: a */
    public p setDuration(long j) {
        super.setDuration(j);
        if (this.mDuration >= 0) {
            int size = this.c.size();
            for (int i = 0; i < size; i++) {
                this.c.get(i).setDuration(j);
            }
        }
        return this;
    }

    /* renamed from: b */
    public p setStartDelay(long j) {
        return (p) super.setStartDelay(j);
    }

    /* renamed from: a */
    public p setInterpolator(TimeInterpolator timeInterpolator) {
        this.e |= 1;
        ArrayList<Transition> arrayList = this.c;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.c.get(i).setInterpolator(timeInterpolator);
            }
        }
        return (p) super.setInterpolator(timeInterpolator);
    }

    /* renamed from: a */
    public p addTarget(View view) {
        for (int i = 0; i < this.c.size(); i++) {
            this.c.get(i).addTarget(view);
        }
        return (p) super.addTarget(view);
    }

    /* renamed from: c */
    public p addTarget(int i) {
        for (int i2 = 0; i2 < this.c.size(); i2++) {
            this.c.get(i2).addTarget(i);
        }
        return (p) super.addTarget(i);
    }

    /* renamed from: a */
    public p addTarget(String str) {
        for (int i = 0; i < this.c.size(); i++) {
            this.c.get(i).addTarget(str);
        }
        return (p) super.addTarget(str);
    }

    /* renamed from: a */
    public p addTarget(Class cls) {
        for (int i = 0; i < this.c.size(); i++) {
            this.c.get(i).addTarget(cls);
        }
        return (p) super.addTarget(cls);
    }

    /* renamed from: a */
    public p addListener(Transition.d dVar) {
        return (p) super.addListener(dVar);
    }

    /* renamed from: d */
    public p removeTarget(int i) {
        for (int i2 = 0; i2 < this.c.size(); i2++) {
            this.c.get(i2).removeTarget(i);
        }
        return (p) super.removeTarget(i);
    }

    /* renamed from: b */
    public p removeTarget(View view) {
        for (int i = 0; i < this.c.size(); i++) {
            this.c.get(i).removeTarget(view);
        }
        return (p) super.removeTarget(view);
    }

    /* renamed from: b */
    public p removeTarget(Class cls) {
        for (int i = 0; i < this.c.size(); i++) {
            this.c.get(i).removeTarget(cls);
        }
        return (p) super.removeTarget(cls);
    }

    /* renamed from: b */
    public p removeTarget(String str) {
        for (int i = 0; i < this.c.size(); i++) {
            this.c.get(i).removeTarget(str);
        }
        return (p) super.removeTarget(str);
    }

    @Override // androidx.transition.Transition
    public Transition excludeTarget(View view, boolean z) {
        for (int i = 0; i < this.c.size(); i++) {
            this.c.get(i).excludeTarget(view, z);
        }
        return super.excludeTarget(view, z);
    }

    @Override // androidx.transition.Transition
    public Transition excludeTarget(String str, boolean z) {
        for (int i = 0; i < this.c.size(); i++) {
            this.c.get(i).excludeTarget(str, z);
        }
        return super.excludeTarget(str, z);
    }

    @Override // androidx.transition.Transition
    public Transition excludeTarget(int i, boolean z) {
        for (int i2 = 0; i2 < this.c.size(); i2++) {
            this.c.get(i2).excludeTarget(i, z);
        }
        return super.excludeTarget(i, z);
    }

    @Override // androidx.transition.Transition
    public Transition excludeTarget(Class cls, boolean z) {
        for (int i = 0; i < this.c.size(); i++) {
            this.c.get(i).excludeTarget(cls, z);
        }
        return super.excludeTarget(cls, z);
    }

    /* renamed from: b */
    public p removeListener(Transition.d dVar) {
        return (p) super.removeListener(dVar);
    }

    @Override // androidx.transition.Transition
    public void setPathMotion(g gVar) {
        super.setPathMotion(gVar);
        this.e |= 4;
        for (int i = 0; i < this.c.size(); i++) {
            this.c.get(i).setPathMotion(gVar);
        }
    }

    private void b() {
        a aVar = new a(this);
        Iterator<Transition> it = this.c.iterator();
        while (it.hasNext()) {
            it.next().addListener(aVar);
        }
        this.a = this.c.size();
    }

    /* access modifiers changed from: package-private */
    public static class a extends m {
        p a;

        a(p pVar) {
            this.a = pVar;
        }

        @Override // androidx.transition.m, androidx.transition.Transition.d
        public void e(Transition transition) {
            if (!this.a.b) {
                this.a.start();
                this.a.b = true;
            }
        }

        @Override // androidx.transition.m, androidx.transition.Transition.d
        public void b(Transition transition) {
            p pVar = this.a;
            pVar.a--;
            if (this.a.a == 0) {
                p pVar2 = this.a;
                pVar2.b = false;
                pVar2.end();
            }
            transition.removeListener(this);
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.transition.Transition
    public void createAnimators(ViewGroup viewGroup, t tVar, t tVar2, ArrayList<s> arrayList, ArrayList<s> arrayList2) {
        long startDelay = getStartDelay();
        int size = this.c.size();
        for (int i = 0; i < size; i++) {
            Transition transition = this.c.get(i);
            if (startDelay > 0 && (this.d || i == 0)) {
                long startDelay2 = transition.getStartDelay();
                if (startDelay2 > 0) {
                    transition.setStartDelay(startDelay2 + startDelay);
                } else {
                    transition.setStartDelay(startDelay);
                }
            }
            transition.createAnimators(viewGroup, tVar, tVar2, arrayList, arrayList2);
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.transition.Transition
    public void runAnimators() {
        if (this.c.isEmpty()) {
            start();
            end();
            return;
        }
        b();
        if (!this.d) {
            for (int i = 1; i < this.c.size(); i++) {
                final Transition transition = this.c.get(i);
                this.c.get(i - 1).addListener(new m() {
                    /* class androidx.transition.p.AnonymousClass1 */

                    @Override // androidx.transition.m, androidx.transition.Transition.d
                    public void b(Transition transition) {
                        transition.runAnimators();
                        transition.removeListener(this);
                    }
                });
            }
            Transition transition2 = this.c.get(0);
            if (transition2 != null) {
                transition2.runAnimators();
                return;
            }
            return;
        }
        Iterator<Transition> it = this.c.iterator();
        while (it.hasNext()) {
            it.next().runAnimators();
        }
    }

    @Override // androidx.transition.Transition
    public void captureStartValues(s sVar) {
        if (isValidTarget(sVar.b)) {
            Iterator<Transition> it = this.c.iterator();
            while (it.hasNext()) {
                Transition next = it.next();
                if (next.isValidTarget(sVar.b)) {
                    next.captureStartValues(sVar);
                    sVar.c.add(next);
                }
            }
        }
    }

    @Override // androidx.transition.Transition
    public void captureEndValues(s sVar) {
        if (isValidTarget(sVar.b)) {
            Iterator<Transition> it = this.c.iterator();
            while (it.hasNext()) {
                Transition next = it.next();
                if (next.isValidTarget(sVar.b)) {
                    next.captureEndValues(sVar);
                    sVar.c.add(next);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.transition.Transition
    public void capturePropagationValues(s sVar) {
        super.capturePropagationValues(sVar);
        int size = this.c.size();
        for (int i = 0; i < size; i++) {
            this.c.get(i).capturePropagationValues(sVar);
        }
    }

    @Override // androidx.transition.Transition
    public void pause(View view) {
        super.pause(view);
        int size = this.c.size();
        for (int i = 0; i < size; i++) {
            this.c.get(i).pause(view);
        }
    }

    @Override // androidx.transition.Transition
    public void resume(View view) {
        super.resume(view);
        int size = this.c.size();
        for (int i = 0; i < size; i++) {
            this.c.get(i).resume(view);
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.transition.Transition
    public void cancel() {
        super.cancel();
        int size = this.c.size();
        for (int i = 0; i < size; i++) {
            this.c.get(i).cancel();
        }
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.transition.Transition
    public void forceToEnd(ViewGroup viewGroup) {
        super.forceToEnd(viewGroup);
        int size = this.c.size();
        for (int i = 0; i < size; i++) {
            this.c.get(i).forceToEnd(viewGroup);
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public p setSceneRoot(ViewGroup viewGroup) {
        super.setSceneRoot(viewGroup);
        int size = this.c.size();
        for (int i = 0; i < size; i++) {
            this.c.get(i).setSceneRoot(viewGroup);
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.transition.Transition
    public void setCanRemoveViews(boolean z) {
        super.setCanRemoveViews(z);
        int size = this.c.size();
        for (int i = 0; i < size; i++) {
            this.c.get(i).setCanRemoveViews(z);
        }
    }

    @Override // androidx.transition.Transition
    public void setPropagation(o oVar) {
        super.setPropagation(oVar);
        this.e |= 2;
        int size = this.c.size();
        for (int i = 0; i < size; i++) {
            this.c.get(i).setPropagation(oVar);
        }
    }

    @Override // androidx.transition.Transition
    public void setEpicenterCallback(Transition.c cVar) {
        super.setEpicenterCallback(cVar);
        this.e |= 8;
        int size = this.c.size();
        for (int i = 0; i < size; i++) {
            this.c.get(i).setEpicenterCallback(cVar);
        }
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.transition.Transition
    public String toString(String str) {
        String transition = super.toString(str);
        for (int i = 0; i < this.c.size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(transition);
            sb.append("\n");
            sb.append(this.c.get(i).toString(str + "  "));
            transition = sb.toString();
        }
        return transition;
    }

    @Override // androidx.transition.Transition, androidx.transition.Transition, java.lang.Object
    public Transition clone() {
        p pVar = (p) super.clone();
        pVar.c = new ArrayList<>();
        int size = this.c.size();
        for (int i = 0; i < size; i++) {
            pVar.a(this.c.get(i).clone());
        }
        return pVar;
    }
}
