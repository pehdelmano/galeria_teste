package com.br.xmaps.galeriateste;

import com.pxr.tutorial.xmltest.R;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.widget.RelativeLayout;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * Classe criada em: 06/02/2013 Classe finalizada em: 06/02/2013
 * 
 * Alteração: 06/02/2013
 * 
 * @author André Ikeda
 * 
 */
@EActivity(R.layout.frag_gallery)
public class GalleryFragment extends Fragment implements ScrollViewListener {
	private ArrayList<GalleryImage> imagens = new ArrayList<GalleryImage>();
	private int imagens_por_linha;
	private int pagina = 0;
	private int linhas_por_pagina;
	private int tamanho_imagens;
	private int margem;
	private int itens;
	private int diferenca_tela;
	private int width;
	private int height;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();
		final ProgressDialog prog = new ProgressDialog(getActivity());
		prog.setMessage("Carregando...");

		scrollView.setScrollViewListener(this);
		DefinePropriedadesDaTela(width, height);
	}

	@Override
	public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
			int oldx, int oldy) {
		// TODO Auto-generated method stub
		if (y == ((diferenca_tela * (pagina + 1)) - scrollView.getHeight())) {
			pagina++;

			AsyncTask<Void, Integer, Void> caregaimagens_task = CarregaTask();
			caregaimagens_task.execute();
		}
	}

	private void CarregaImagens(String url) {
		JSONObject json = JSONfunctions.getJSONfromURL(url);

		try {
			JSONArray fotos = json.getJSONArray("fotos");

			for (int i = 0; i < fotos.length(); i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				JSONObject e = fotos.getJSONObject(i);
				map.put("id_foto", e.getString("id_foto"));
				map.put("legenda", e.getString("legenda"));
				map.put("latitude", e.getString("latitude"));
				map.put("longitude", e.getString("longitude"));
				map.put("usuario", e.getString("usuario"));
				map.put("foto", e.getString("foto"));
				map.put("thumb", e.getString("thumb"));
				map.put("esporte", e.getString("esporte"));
				GalleryImage new_image = new GalleryImage(map.values());
				imagens.add(new_image);
			}
		} catch (JSONException e) {
			Log.e("", "Erro: " + e.toString());
		}

		Log.i("tamanho_array", "" + imagens.size());

	}

	private Drawable LoadImageFromWebOperations(String UrlIimagem) {
		try {
			InputStream is = (InputStream) new URL(UrlIimagem).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			System.out.println("Exc=" + e);
			return null;
		}
	}

	private void ColocaImagensNaTela() {

		for (int x = 0; x < linhas_por_pagina; x++) {
			
			LinearLayout lista_galeria = new LinearLayout(getActivity());
			LinearLayout.LayoutParams lpar = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
					LinearLayout.HORIZONTAL);
			lpar.gravity = Gravity.CENTER_HORIZONTAL;
			if (x == linhas_por_pagina - 1) {
				lpar.setMargins(0, margem, 0, 0);
			} else {
				lpar.setMargins(0, margem, 0, 0);
			}
			rl.addView(lista_galeria, lpar);

			for (int i = 0; i < imagens_por_linha; i++) {
				if (((x * imagens_por_linha) + i + (pagina * itens)) < imagens
						.size()) {
					ImageView iv = new ImageView(getActivity());
					iv.setTag("http://m.hb20.com.br/upload/desafiox/"
							+ imagens.get(
									((x * imagens_por_linha) + i)
											+ (pagina * itens)).getFoto());
					iv.setImageDrawable(LoadImageFromWebOperations("http://m.hb20.com.br/upload/desafiox/"
							+ imagens.get(
									((x * imagens_por_linha) + i)
											+ (pagina * itens)).getThumb()));
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
							tamanho_imagens, tamanho_imagens,
							LinearLayout.HORIZONTAL);
					if (i == 0) {
						lp.setMargins(margem / 2, 0, 0, 0);
					} else if (i == (imagens_por_linha - 1)) {
						lp.setMargins(margem, 0, margem / 2, 0);
					} else {
						lp.setMargins(margem, 0, 0, 0);
					}
					
					OnTouchListener galeriaTouchListener = new OnTouchListener() {
						// onTouch Handler
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							
							Animation myFadeInAnimation;
							switch (event.getAction()) {
							case MotionEvent.ACTION_DOWN:
								return true;
							case MotionEvent.ACTION_UP:
								String tag = (String) v.getTag();
								image.setImageDrawable(LoadImageFromWebOperations(tag));
								myFadeInAnimation = AnimationUtils
										.loadAnimation(getActivity(),
												R.anim.fadein);
								fundo.startAnimation(myFadeInAnimation);
								image.startAnimation(myFadeInAnimation);
								fundo.setVisibility(1);
								image.setVisibility(1);
								return true;
							case MotionEvent.ACTION_MOVE:
								return false;
							}

							return false;
						}
					};
					iv.setOnTouchListener(galeriaTouchListener);

					lista_galeria.addView(iv, lp);
				} else {
					rl.removeView(lista_galeria);
				}
			}
		}

	}

	private void DefinePropriedadesDaTela(int width, int height) {
		imagens_por_linha = 3;
		margem = width / 12;
		tamanho_imagens = width / 4;
		linhas_por_pagina = (height / (margem + tamanho_imagens) + 1);
		itens = (imagens_por_linha * linhas_por_pagina);

		diferenca_tela = (margem + tamanho_imagens) * (linhas_por_pagina);

		OnTouchListener fadeouttouch = new OnTouchListener() {
			// onTouch Handler
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					return true;
				case MotionEvent.ACTION_UP:
					Animation myFadeInAnimation = AnimationUtils.loadAnimation(
							getActivity(), R.anim.fadeout);
					fundo.startAnimation(myFadeInAnimation);
					image.startAnimation(myFadeInAnimation);
					image.setVisibility(4);
					fundo.setVisibility(4);
					return true;
				case MotionEvent.ACTION_MOVE:
					return false;
				}

				return false;
			}
		};

		image.setOnTouchListener(fadeouttouch);
		fundo.setOnTouchListener(fadeouttouch);
	}

	private AsyncTask<Void, Integer, Void> CarregaTask() {
		final ProgressDialog prog = new ProgressDialog(getActivity());
		prog.setMessage("Carregando...");
		AsyncTask<Void, Integer, Void> caregaimagens_task = new AsyncTask<Void, Integer, Void>() {

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				// Display progressDialog before download starts
				prog.show();
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				ColocaImagensNaTela();
				prog.hide();
				// Hide Progress Dialog else use dismiss() to dismiss the dialog
			}

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub

				CarregaImagens("http://m.hb20.com.br/desafiox/fotos/list/pagina/"
						+ pagina + "/itens/" + itens);
				return null;
			}

		};
		return caregaimagens_task;
	}

	@ViewById(R.id.ly_skin)
	RelativeLayout lySkin;
	@ViewById(R.id.scrollview)
	ObservableScrollView scrollView;
	@ViewById(R.id.layout)
	LinearLayout rl;
	@ViewById(R.id.imagem_grande)
	ImageView image;
	@ViewById (R.id.fundo)
	View fundo;
}
