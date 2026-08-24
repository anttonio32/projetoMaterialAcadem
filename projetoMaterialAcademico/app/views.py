from django.shortcuts import render
from .models import *

def home(request): 
    return render(request, 'home/index.html')

def listagemAluno(request): 
    #pega os dados da tela e salva no banco de dados 
    novo_Aluno = Aluno()
    novo_Aluno.matricula = request.POST.get('matricula')
    novo_Aluno.nome = request.POST.get('nome')
    novo_Aluno.email = request.POST.get('email')
    novo_Aluno.senha = request.POST.get('senha')
    novo_Aluno.telefone = request.POST.get('telefone')
    novo_Aluno.save()