from django.urls import path
from app import views


urlpatterns = [
    #rota,view responsavel, nome referencia
    #pagina home
    path('',views.home, name='home'), 
]
