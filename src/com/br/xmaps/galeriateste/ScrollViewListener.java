package com.br.xmaps.galeriateste;

//Interface que acessa os listeners de eventos num scrollview
public interface ScrollViewListener {
	//M�todo de checagem da altera��o da posi��o do scroll
	void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
}
