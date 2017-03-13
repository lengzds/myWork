package com.testsoot;

import java.util.Collections;
import java.util.List;

import soot.PackManager;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Transform;
import soot.options.Options;

public class test {
	public static void main(String[] args) {
		String[] args2 =
            {
                "-android-jars", "E:\\adt-bundle\\sdk\\platforms",
                "-process-dir", "explorer.apk",
                "-ire",
                "-pp",
                "-allow-phantom-refs",
                "-w",
    			"-p", "cg", "enabled:true",
    			//disable default optimization
    			"-p", "jb.cp-ule", "enabled:false",
    			"-p", "jb.uce", "enabled:false",
    			"-p", "jb.ne", "enabled:false",
    			"-p", "jb.ule", "enabled:false",
    			"-p", "jb.dae", "enabled:false",
    			
    			"-p", "jop.cse", "enabled:false",
    			"-p", "jop.nce", "enabled:false",
    			"-p", "jop.dae", "enabled:false",
    			"-p", "jop.uce1", "enabled:false",
    			"-p", "jop.uce2", "enabled:false",
    			"-p", "jop.ule", "enabled:false",
    			
    			"-p", "bb.ule", "enabled:false",
    			"-p", "bb.pho", "enabled:false",
    			"-p", "bb.lso", "enabled:false",
            };
		
		String jarPath = "E:\\adt-bundle\\sdk\\platforms";
//		// 璁剧疆瑕佸垎鏋愮殑APK鏂囦欢
		String apk = "explorer.apk";//"./hello4_for30.apk";
		soot.G.reset();
		Options.v().set_src_prec(Options.src_prec_apk);
		Options.v().set_process_dir(Collections.singletonList(apk));
		Options.v().set_force_android_jar(jarPath + "/android-19/android.jar");
		Options.v().set_whole_program(true);
		Options.v().set_allow_phantom_refs(true);
		Options.v().set_output_format(Options.output_format_jimple);
//		Options.v().setPhaseOption("cg.spark verbose:false", "on");//true
		Scene.v().loadNecessaryClasses();
//		SootMethod entryPoint = app.getEntryPointCreator().createDummyMain();
//		Options.v().set_main_class(entryPoint.getSignature());
//		Scene.v().setEntryPoints(Collections.singletonList(entryPoint));
//		PackManager.v().runPacks();
		
		
		soot.Main.main(args2);
			
			for (SootClass sc : Scene.v().getClasses()){// 闇�瑕佹煡鐪嬩竴涓嬭繖閲岀殑Jimple鐮�
//				System.out.println("++++++++" + sc);// 鎵�鏈夌殑鍑芥暟
				if (sc.toString().contains("com.")) {
					System.out.println("sc.getMethods : " + sc.getMethods());
					List<SootMethod> a = sc.getMethods();
					for (int i = 0; i < a.size(); i++) {
						if (a.get(i).hasActiveBody())
							System.out.println("a.get(i).getActiveBody : " + a.get(i).getActiveBody());
					}
				}

				// System.out.println("+++++++++++++++++"+sc.getMethodByName("java.lang.Integer: void <init>(int)"));
			}
			
			System.out.println("getCallGraph : "+Scene.v().getCallGraph());
		}
}
