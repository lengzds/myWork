package com.testsoot;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import soot.Body;
import soot.BodyTransformer;
import soot.Local;
import soot.PackManager;
import soot.PatchingChain;
import soot.RefType;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Transform;
import soot.Unit;
import soot.jimple.AbstractStmtSwitch;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.Jimple;
import soot.jimple.StringConstant;
import soot.options.Options;

public class official {

	public static void main(String[] args) {
		String[] args2 = { "-w", "-allow-phantom-refs", "-src-prec", "apk",
				"-android-jars", "E:\\adt-bundle\\sdk\\platforms",
				"-process-dir", "hello.apk", };

		// prefer Android APK files// -src-prec apk
		Options.v().set_src_prec(Options.src_prec_apk);

		// output as APK, too//-f J
		Options.v().set_output_format(Options.output_format_jimple);

//		Options.v().setPhaseOption("cg.spark verbose:true", "on");// true
//		Options.v().setPhaseOption("cg.cha verbose:true", "on");
//		Options.v().setPhaseOption("wjtp verbose:true", "on");
		Options.v().setPhaseOption("wjap verbose:true", "on");

		// Options.v().set_force_android_jar("/home/zds/android/adt-bundle-linux-x86_64-20130917/sdk/platforms/android-19/android.jar");

		// Options.v().set_whole_program(true);
		// Options.v().set_allow_phantom_refs(true);
		// Options.v().set_force_android_jar("E:\\adt-bundle\\sdk\\platforms\\android-20\\android.jar");

		// resolve the PrintStream and System soot-classes
		Scene.v().addBasicClass("java.io.PrintStream", SootClass.SIGNATURES);
		Scene.v().addBasicClass("java.lang.String", SootClass.SIGNATURES);
		// Scene.v().loadNecessaryClasses();

		PackManager.v().getPack("jtp")
				.add(new Transform("jtp.myInstrumenter", new BodyTransformer() {

					@Override
					protected void internalTransform(final Body b,
							String phaseName,
							@SuppressWarnings("rawtypes") Map options) {
						final PatchingChain<Unit> units = b.getUnits();

						// important to use snapshotIterator here
						for (Iterator<Unit> iter = units.snapshotIterator(); iter
								.hasNext();) {
							final Unit u = iter.next();
							u.apply(new AbstractStmtSwitch() {

								public void caseInvokeStmt(InvokeStmt stmt) {
									InvokeExpr invokeExpr = stmt
											.getInvokeExpr();
									if (invokeExpr.getMethod().getName()
											.equals("onDraw")) {

										Local tmpRef = addTmpRef(b);
										Local tmpString = addTmpString(b);

										// insert
										// "tmpRef = java.lang.System.out;"
										units.insertBefore(
												Jimple.v()
														.newAssignStmt(
																tmpRef,
																Jimple.v()
																		.newStaticFieldRef(
																				Scene.v()
																						.getField(
																								"<java.lang.System: java.io.PrintStream out>")
																						.makeRef())),
												u);

										// insert "tmpLong = 'HELLO';"
										units.insertBefore(
												Jimple.v().newAssignStmt(
														tmpString,
														StringConstant
																.v("HELLO")), u);

										// insert "tmpRef.println(tmpString);"
										SootMethod toCall = Scene
												.v()
												.getSootClass(
														"java.io.PrintStream")
												.getMethod(
														"void println(java.lang.String)");
										units.insertBefore(
												Jimple.v()
														.newInvokeStmt(
																Jimple.v()
																		.newVirtualInvokeExpr(
																				tmpRef,
																				toCall.makeRef(),
																				tmpString)),
												u);

										// check that we did not mess up the
										// Jimple
										b.validate();
									}
								}

							});
						}
					}

				}));

		soot.Main.main(args2);

		// for (SootClass sc : Scene.v().getClasses()) {//
		// 闇�瑕佹煡鐪嬩竴涓嬭繖閲岀殑Jimple鐮�
		// // System.out.println("++++++++" + sc);// 鎵�鏈夌殑鍑芥暟
		// if (sc.toString().contains("com.")) {
		// System.out.println("sc.getMethods : " + sc.getMethods());
		// List<SootMethod> a = sc.getMethods();
		// for (int i = 0; i < a.size(); i++) {
		// if (a.get(i).hasActiveBody())
		// System.out.println("a.get(i).getActiveBody : " +
		// a.get(i).getActiveBody());
		// }
		// }
		// }

	}

	private static Local addTmpRef(Body body) {
		Local tmpRef = Jimple.v().newLocal("tmpRef",
				RefType.v("java.io.PrintStream"));
		body.getLocals().add(tmpRef);
		return tmpRef;
	}

	private static Local addTmpString(Body body) {
		Local tmpString = Jimple.v().newLocal("tmpString",
				RefType.v("java.lang.String"));
		body.getLocals().add(tmpString);
		return tmpString;
	}
}
