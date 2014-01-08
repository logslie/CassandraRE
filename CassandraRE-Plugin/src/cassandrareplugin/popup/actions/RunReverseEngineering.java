package cassandrareplugin.popup.actions;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.indigital.CassandraCodeRE.RunRE;

public class RunReverseEngineering implements IObjectActionDelegate {
	Logger logger = Logger.getLogger(RunReverseEngineering.class.getName());
	private Shell shell;
	/**
	 * ISelection is cached in method selectionChanged for later use it in run()
	 */
	private ISelection cachedSelection;

	/**
	 * Constructor for Action1.
	 */
	public RunReverseEngineering() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {

		StructuredSelection selection = (StructuredSelection) cachedSelection;
		IFile file = (IFile) selection.getFirstElement();
		logger.info("Path resource:" + file.getFullPath().toString());
		String packageName = "";

		try {
			packageName = file.getPersistentProperty(new QualifiedName("RE", "package"));
			logger.info("Package name:" + packageName);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		RunRE runRE = new RunRE();
		String[] args = new String[3];
		args[0] = file.getLocation().toString();
		args[1] = packageName;
		args[2] = file.getProject().getLocation().toString();
		logger.info(file.getProject().getLocation());
		logger.info("Argument 3:" + args[2]);
		runRE.runRE(args);
		MessageDialog.openInformation(shell, "CassandraRE-Plugin",
		"Run was executed!");

	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */

	public void selectionChanged(IAction action, ISelection selection) {

		logger.debug(selection);
		cachedSelection = selection;
	}

}
