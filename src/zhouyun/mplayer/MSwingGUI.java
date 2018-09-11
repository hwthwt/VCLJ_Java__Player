package zhouyun.mplayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class MSwingGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	// VLC ��Ƶ������
	private EmbeddedMediaPlayerComponent mediaPlayerComponent = null;
	// ����һ��������
	private MControl mControl = new MControl();
	private static JFrame frame;
	private JButton loadButton;
	private JButton startButton;
	private JButton pauseButton;
	private JButton accelerateButton;
	private JButton rewindButton;
	private JButton fastButton;
	private JButton exitButton;
	private JProgressBar progressBar;
	private JLabel timeLabel;
	
	public MControl getMControl(){
		return mControl;
	}
	public JProgressBar getProgressBar(){
		return progressBar;
	}
	public JLabel getTimeLabel(){
		return timeLabel;
	}

	public EmbeddedMediaPlayerComponent getMediaPlayerComponent() {
		return mediaPlayerComponent;
	}

	/**
	 * Create the frame.
	 */
	public MSwingGUI() {
		frame = new JFrame("˽�˶�����Ƶ������");
		frame.setBounds(100, 100, 1280, 800);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// �ر�ʱ�������ͷ���Դ
				mediaPlayerComponent.release();
				System.exit(0);
			}
		});

		// ����ҳ�沼��
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());

		// ������������panel�У�����������λ��
		if( mediaPlayerComponent == null)
		{
			synchronized (EmbeddedMediaPlayerComponent.class) {
				if( mediaPlayerComponent == null)
				{
					mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
				}			
			}
		}
		
		contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);
		JPanel upControlsPane = new JPanel();
		upControlsPane.setLayout(new BorderLayout());
		
		// ������
		JPanel barAndTimePane = new JPanel();
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBackground(Color.LIGHT_GRAY);
		progressBar.setForeground(Color.BLUE);
		progressBar.setMaximum(mControl.getmaxBar());
		upControlsPane.add(barAndTimePane, BorderLayout.NORTH);
		timeLabel = new JLabel("time");
		timeLabel.setBackground(Color.WHITE);
		barAndTimePane.setLayout(new BoxLayout(barAndTimePane, BoxLayout.X_AXIS));
		barAndTimePane.add(progressBar);
		barAndTimePane.add(timeLabel);
		
		// BUTTON�Ĳ���
		JPanel controlsPane = new JPanel();
		loadButton = new JButton("������Ƶ");
		controlsPane.add(loadButton);
		startButton = new JButton("��ʼ");
		controlsPane.add(startButton);
		pauseButton = new JButton("��ͣ");
		controlsPane.add(pauseButton);
		fastButton = new JButton("���");
		controlsPane.add(fastButton);
		rewindButton = new JButton("����");
		controlsPane.add(rewindButton);
		accelerateButton = new JButton("����");
		controlsPane.add(accelerateButton);
		exitButton = new JButton("����");
		controlsPane.add(exitButton);
		upControlsPane.add(controlsPane, BorderLayout.SOUTH);
		contentPane.add(upControlsPane, BorderLayout.SOUTH);
		
		// ��ť�����¼�
		progressBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(0 == mediaPlayerComponent.getMediaPlayer().getLength() )
					return;
				float proChangeBarPosition = (float)(progressBar.getMousePosition().getX() - progressBar.getX()) / (progressBar.getWidth());
				mediaPlayerComponent.getMediaPlayer().setTime( (long)(mediaPlayerComponent.getMediaPlayer().getLength() * proChangeBarPosition) );
			}
		});
		
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mControl.mload(mediaPlayerComponent);
			}
		});
		
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mControl.mstart(mediaPlayerComponent);
			}
		});

		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mControl.mpause(mediaPlayerComponent);
			}
		});
		
		accelerateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mControl.maccelerate(mediaPlayerComponent);
			}
		});

		fastButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mControl.mfast(mediaPlayerComponent);
			}
		});

		rewindButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mControl.mrewind(mediaPlayerComponent);
			}
		});

		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mControl.mexit(mediaPlayerComponent);
			}
		});

		// ��ʾ
		frame.setContentPane(contentPane);
		frame.setVisible(true);

	}

}
