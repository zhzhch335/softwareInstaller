package softwareInstaller;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Toolkit;

public class VisualSwingTest {

	public static void main(String[] args) {

		/*
		 * 使用substance美化界面
		 */
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			UIManager.setLookAndFeel(new org.jvnet.substance.skin.SubstanceBusinessBlueSteelLookAndFeel());
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		/*
		 * 使用构造函数加载窗体和组件 （线程安全）
		 */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
					//VisualSwingTest window = new VisualSwingTest();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 用于加载窗体和组件的构造函数
	 */
	public VisualSwingTest() {
		initialize();
	}

	/**
	 * 窗体和组件初始化
	 */
	private static void initialize() {
		// 窗体
		JFrame frmHey = new JFrame();
		frmHey.setIconImage(
				Toolkit.getDefaultToolkit().getImage(VisualSwingTest.class.getResource("/resource/computer.png")));
		frmHey.setFont(new Font("微软雅黑", Font.BOLD, 12));
		frmHey.getContentPane().setFont(new Font("方正兰亭超细黑简体", Font.PLAIN, 12));
		frmHey.setTitle("软件注册机");
		frmHey.setBounds(100, 100, 516, 424);
		frmHey.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHey.getContentPane().setLayout(null);

		// 文本框
		JTextField initText = new JTextField();
		initText.setBounds(69, 84, 211, 21);
		frmHey.getContentPane().add(initText);
		initText.setColumns(10);
		JTextField fetchText = new JTextField();
		fetchText.setBounds(69, 191, 211, 21);
		frmHey.getContentPane().add(fetchText);
		fetchText.setColumns(10);

		// 提示标签
		JLabel initHint = new JLabel("请输入生成文件的路径：");
		initHint.setFont(new Font("微软雅黑", Font.BOLD, 12));
		initHint.setBounds(69, 56, 179, 15);
		frmHey.getContentPane().add(initHint);
		JLabel fetchHint = new JLabel("请输入要核对的文件路径：");
		fetchHint.setFont(new Font("微软雅黑", Font.BOLD, 12));
		fetchHint.setBounds(69, 160, 161, 15);
		frmHey.getContentPane().add(fetchHint);

		// 创建一个空对话框作为文件选择的容器
		JDialog dialog = new JDialog();
		dialog.setBounds(200, 200, 640, 480);

		// 获取桌面路径
		File fsv = FileSystemView.getFileSystemView().getHomeDirectory();
		JFileChooser chooser = new JFileChooser(fsv);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("密钥文件 .key", "key");
		chooser.setFileFilter(filter);

		// 浏览按钮，用于唤起文件选择对话框
		JButton initFile = new JButton("浏览...");
		initFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int returnVal = chooser.showSaveDialog(dialog);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String url = chooser.getCurrentDirectory().getAbsolutePath() + "\\"
							+ chooser.getSelectedFile().getName();
					if (!url.endsWith(".key")) {
						url = url + ".key";
					}
					initText.setText(url);

				}
			}
		});
		initFile.setBounds(290, 83, 79, 23);
		frmHey.getContentPane().add(initFile);
		JButton fetchFile = new JButton("浏览...");

		// 浏览按钮，用于唤起文件保存对话框
		// 尝试使用通用方法写事件相应（也就是说所有动作都会触发事件）
		fetchFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = chooser.showOpenDialog(dialog);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fetchText.setText(chooser.getSelectedFile().getPath());
				}
			}
		});
		fetchFile.setBounds(290, 190, 79, 23);
		frmHey.getContentPane().add(fetchFile);

		/*
		 * 操作执行按钮
		 * 
		 */
		// 生成按钮
		JButton init = new JButton("生成");
		init.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					StringBuffer str = new StringBuffer("5858");
					String inputstr = JOptionPane.showInputDialog(dialog, "要生成密钥，必须输入管理员密码：", "请输入密码",
							JOptionPane.QUESTION_MESSAGE);
					if (inputstr != null && inputstr.contentEquals(str)) {
						String[] info = Main.ownKey();
						Main.createKey(initText.getText());
						JOptionPane.showMessageDialog(dialog, "写入文件成功，写入路径为" + initText.getText(), "完成",
								JOptionPane.INFORMATION_MESSAGE);
						JOptionPane.showMessageDialog(dialog,
								"CPUID:" + info[0] + "\n" + "DiskID:" + info[1] + "\n" + "序列号：" + info[4], "系统信息",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(dialog, "密码错误，生成失败！", "错误", JOptionPane.ERROR_MESSAGE);
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(dialog, "无权限写入文件，请稍后或更换路径再试", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		init.setBounds(376, 83, 69, 23);
		frmHey.getContentPane().add(init);

		// 核对按钮
		JButton fetch = new JButton("核对");
		fetch.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					if (Main.checkKey(fetchText.getText())) {
						String[] info = Main.ownKey();
						JOptionPane.showMessageDialog(dialog, "软件认证成功，请继续使用", "成功", JOptionPane.INFORMATION_MESSAGE);
						JOptionPane.showMessageDialog(dialog,
								"CPUID:" + info[0] + "\n" + "DiskID:" + info[1] + "\n" + "序列号：" + info[4], "系统信息",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(dialog, "认证失败，请重试", "失败", JOptionPane.WARNING_MESSAGE);
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(dialog, "找不到文件或无法读取该文件，请重试", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		fetch.setBounds(376, 190, 69, 23);
		frmHey.getContentPane().add(fetch);

		/*
		 * 版本和功能开关
		 * 
		 */

		// 版本提示标签
		JLabel vHint = new JLabel("当前版本号为：");
		vHint.setBounds(59, 279, 96, 15);
		frmHey.getContentPane().add(vHint);

		// 版本文本框
		JTextField vText = new JTextField(Main.softwareVersion);
		vText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				StringBuffer str = new StringBuffer("5858");
				String inputstr = JOptionPane.showInputDialog(dialog, "要修改版本号，必须输入管理员密码：：", "请输入密码",
						JOptionPane.QUESTION_MESSAGE);
				if (inputstr != null && inputstr.contentEquals(str)) {
					Main.softwareVersion = vText.getText();
				} else {
					JOptionPane.showMessageDialog(dialog, "密码错误，修改失败！", "错误", JOptionPane.ERROR_MESSAGE);
					vText.setText(Main.softwareVersion);
				}
			}
		});
		vText.setBounds(177, 276, 148, 21);
		frmHey.getContentPane().add(vText);
		vText.setColumns(10);

		// 功能输入标签
		JLabel fHint = new JLabel("功能开关：");
		fHint.setBounds(59, 322, 79, 15);
		frmHey.getContentPane().add(fHint);

		// 功能切换按钮
		JToggleButton fSwitch = new JToggleButton("开", true);
		fSwitch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StringBuffer str = new StringBuffer("5858");
				String inputstr = JOptionPane.showInputDialog(dialog, "要切换功能开关，必须输入管理员密码：：", "请输入密码",
						JOptionPane.QUESTION_MESSAGE);
				if (inputstr != null && inputstr.contentEquals(str)) {
					if (fSwitch.isSelected()) {
						fSwitch.setText("开");
						Main.funcationSwitch = "true";
					} else {
						fSwitch.setText("关");
						Main.funcationSwitch = "false";
					}
				} else {
					JOptionPane.showMessageDialog(dialog, "密码错误，修改失败！", "错误", JOptionPane.ERROR_MESSAGE);
					if (fSwitch.isSelected()) {
						fSwitch.setSelected(false);
					} else {
						fSwitch.setSelected(true);
					}
				}

			}
		});
		fSwitch.setBounds(177, 318, 135, 23);
		frmHey.getContentPane().add(fSwitch);

		// 显示窗体
		frmHey.setVisible(true);
	}
}
