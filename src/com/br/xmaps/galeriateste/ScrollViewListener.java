package com.br.xmaps.galeriateste;

//Interface que acessa os listeners de eventos num scrollview
public interface ScrollViewListener {
	//Método de checagem da alteração da posição do scroll
	void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
}
