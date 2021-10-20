package androidx.transition;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.l;
import androidx.transition.Transition;
import java.util.ArrayList;
import java.util.List;

public class e extends l {
    @Override // androidx.fragment.app.l
    public boolean a(Object obj) {
        return obj instanceof Transition;
    }

    @Override // androidx.fragment.app.l
    public Object b(Object obj) {
        if (obj != null) {
            return ((Transition) obj).clone();
        }
        return null;
    }

    @Override // androidx.fragment.app.l
    public Object c(Object obj) {
        if (obj == null) {
            return null;
        }
        p pVar = new p();
        pVar.a((Transition) obj);
        return pVar;
    }

    @Override // androidx.fragment.app.l
    public void a(Object obj, View view, ArrayList<View> arrayList) {
        p pVar = (p) obj;
        List<View> targets = pVar.getTargets();
        targets.clear();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            a(targets, arrayList.get(i));
        }
        targets.add(view);
        arrayList.add(view);
        a(pVar, arrayList);
    }

    @Override // androidx.fragment.app.l
    public void a(Object obj, View view) {
        if (view != null) {
            final Rect rect = new Rect();
            a(view, rect);
            ((Transition) obj).setEpicenterCallback(new Transition.c() {
                /* class androidx.transition.e.AnonymousClass1 */

                @Override // androidx.transition.Transition.c
                public Rect a(Transition transition) {
                    return rect;
                }
            });
        }
    }

    @Override // androidx.fragment.app.l
    public void a(Object obj, ArrayList<View> arrayList) {
        Transition transition = (Transition) obj;
        if (transition != null) {
            int i = 0;
            if (transition instanceof p) {
                p pVar = (p) transition;
                int a = pVar.a();
                while (i < a) {
                    a(pVar.b(i), arrayList);
                    i++;
                }
            } else if (!a(transition) && a((List) transition.getTargets())) {
                int size = arrayList.size();
                while (i < size) {
                    transition.addTarget(arrayList.get(i));
                    i++;
                }
            }
        }
    }

    private static boolean a(Transition transition) {
        return !a(transition.getTargetIds()) || !a(transition.getTargetNames()) || !a(transition.getTargetTypes());
    }

    @Override // androidx.fragment.app.l
    public Object a(Object obj, Object obj2, Object obj3) {
        p pVar = new p();
        if (obj != null) {
            pVar.a((Transition) obj);
        }
        if (obj2 != null) {
            pVar.a((Transition) obj2);
        }
        if (obj3 != null) {
            pVar.a((Transition) obj3);
        }
        return pVar;
    }

    @Override // androidx.fragment.app.l
    public void b(Object obj, final View view, final ArrayList<View> arrayList) {
        ((Transition) obj).addListener(new Transition.d() {
            /* class androidx.transition.e.AnonymousClass2 */

            @Override // androidx.transition.Transition.d
            public void a(Transition transition) {
            }

            @Override // androidx.transition.Transition.d
            public void c(Transition transition) {
            }

            @Override // androidx.transition.Transition.d
            public void d(Transition transition) {
            }

            @Override // androidx.transition.Transition.d
            public void e(Transition transition) {
            }

            @Override // androidx.transition.Transition.d
            public void b(Transition transition) {
                transition.removeListener(this);
                view.setVisibility(8);
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    ((View) arrayList.get(i)).setVisibility(0);
                }
            }
        });
    }

    @Override // androidx.fragment.app.l
    public Object b(Object obj, Object obj2, Object obj3) {
        Transition transition = (Transition) obj;
        Transition transition2 = (Transition) obj2;
        Transition transition3 = (Transition) obj3;
        if (transition != null && transition2 != null) {
            transition = new p().a(transition).a(transition2).a(1);
        } else if (transition == null) {
            transition = transition2 != null ? transition2 : null;
        }
        if (transition3 == null) {
            return transition;
        }
        p pVar = new p();
        if (transition != null) {
            pVar.a(transition);
        }
        pVar.a(transition3);
        return pVar;
    }

    @Override // androidx.fragment.app.l
    public void a(ViewGroup viewGroup, Object obj) {
        n.a(viewGroup, (Transition) obj);
    }

    @Override // androidx.fragment.app.l
    public void a(Object obj, final Object obj2, final ArrayList<View> arrayList, final Object obj3, final ArrayList<View> arrayList2, final Object obj4, final ArrayList<View> arrayList3) {
        ((Transition) obj).addListener(new Transition.d() {
            /* class androidx.transition.e.AnonymousClass3 */

            @Override // androidx.transition.Transition.d
            public void a(Transition transition) {
            }

            @Override // androidx.transition.Transition.d
            public void b(Transition transition) {
            }

            @Override // androidx.transition.Transition.d
            public void c(Transition transition) {
            }

            @Override // androidx.transition.Transition.d
            public void d(Transition transition) {
            }

            @Override // androidx.transition.Transition.d
            public void e(Transition transition) {
                Object obj = obj2;
                if (obj != null) {
                    e.this.b(obj, arrayList, (ArrayList<View>) null);
                }
                Object obj2 = obj3;
                if (obj2 != null) {
                    e.this.b(obj2, arrayList2, (ArrayList<View>) null);
                }
                Object obj3 = obj4;
                if (obj3 != null) {
                    e.this.b(obj3, arrayList3, (ArrayList<View>) null);
                }
            }
        });
    }

    @Override // androidx.fragment.app.l
    public void a(Object obj, ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        p pVar = (p) obj;
        if (pVar != null) {
            pVar.getTargets().clear();
            pVar.getTargets().addAll(arrayList2);
            b((Object) pVar, arrayList, arrayList2);
        }
    }

    @Override // androidx.fragment.app.l
    public void b(Object obj, ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        int i;
        Transition transition = (Transition) obj;
        int i2 = 0;
        if (transition instanceof p) {
            p pVar = (p) transition;
            int a = pVar.a();
            while (i2 < a) {
                b((Object) pVar.b(i2), arrayList, arrayList2);
                i2++;
            }
        } else if (!a(transition)) {
            List<View> targets = transition.getTargets();
            if (targets.size() == arrayList.size() && targets.containsAll(arrayList)) {
                if (arrayList2 == null) {
                    i = 0;
                } else {
                    i = arrayList2.size();
                }
                while (i2 < i) {
                    transition.addTarget(arrayList2.get(i2));
                    i2++;
                }
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    transition.removeTarget(arrayList.get(size));
                }
            }
        }
    }

    @Override // androidx.fragment.app.l
    public void b(Object obj, View view) {
        if (obj != null) {
            ((Transition) obj).addTarget(view);
        }
    }

    @Override // androidx.fragment.app.l
    public void c(Object obj, View view) {
        if (obj != null) {
            ((Transition) obj).removeTarget(view);
        }
    }

    @Override // androidx.fragment.app.l
    public void a(Object obj, final Rect rect) {
        if (obj != null) {
            ((Transition) obj).setEpicenterCallback(new Transition.c() {
                /* class androidx.transition.e.AnonymousClass4 */

                @Override // androidx.transition.Transition.c
                public Rect a(Transition transition) {
                    Rect rect = rect;
                    if (rect == null || rect.isEmpty()) {
                        return null;
                    }
                    return rect;
                }
            });
        }
    }
}
