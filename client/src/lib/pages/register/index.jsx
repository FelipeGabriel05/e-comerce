import { useState } from 'react';

const Register = () => {
  const [nome, setNome] = useState('');
  const [endereco, setEndereco] = useState('');
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');

  function handleSubmit(event) {
    event.preventDefault();
    console.log(nome);
    console.log(endereco);
    console.log(email);
    console.log(senha);

    const novoCadastro = { nome, endereco, email, senha };
    console.log(novoCadastro);
  }

  return (
    <div className="bg-[#1A1B2F] text-white pt-6 px-12 pb-12 flex min-h-full w-125 flex-col justify-center rounded-2xl shadow-lg mx-auto">
      <div className="text-center">
        <img
          src="https://tailwindcss.com/plus-assets/img/logos/mark.svg?color=indigo&shade=500"
          alt="Your Company"
          className="mx-auto h-10 w-auto"
        />
        <h2 className="mt-4 text-2xl font-bold tracking-tight">
          Formulário de Cadastro
        </h2>
      </div>

      <form onSubmit={handleSubmit} className="mt-8 space-y-5">
        <div>
          <label
            htmlFor="nome"
            className="block text-sm font-medium text-gray-100"
          >
            Nome Completo
          </label>
          <input
            id="nome"
            type="text"
            onChange={(event) => setNome(event.target.value)}
            required
            className="mt-2 block w-full rounded-md bg-white/5 px-3 py-2 text-white outline outline-1 outline-white/10 focus:outline-2 focus:outline-indigo-500"
          />
        </div>

        <div>
          <label
            htmlFor="endereco"
            className="block text-sm font-medium text-gray-100"
          >
            Endereço residencial
          </label>
          <input
            id="endereco"
            type="text"
            onChange={(event) => setEndereco(event.target.value)}
            required
            className="mt-2 block w-full rounded-md bg-white/5 px-3 py-2 text-white outline outline-1 outline-white/10 focus:outline-2 focus:outline-indigo-500"
          />
        </div>

        <div>
          <label
            htmlFor="email"
            className="block text-sm font-medium text-gray-100"
          >
            E-mail
          </label>
          <input
            id="email"
            type="email"
            onChange={(event) => setEmail(event.target.value)}
            required
            className="mt-2 block w-full rounded-md bg-white/5 px-3 py-2 text-white outline outline-1 outline-white/10 focus:outline-2 focus:outline-indigo-500"
          />
        </div>

        <div>
          <label
            htmlFor="password"
            className="block text-sm font-medium text-gray-100"
          >
            Senha
          </label>
          <input
            id="password"
            type="password"
            onChange={(event) => setSenha(event.target.value)}
            required
            className="mt-2 block w-full rounded-md bg-white/5 px-3 py-2 text-white outline outline-1 outline-white/10 focus:outline-2 focus:outline-indigo-500"
          />
        </div>

        <button
          type="submit"
          className="mt-4 w-full rounded-md bg-indigo-500 py-2 font-semibold hover:bg-indigo-400"
        >
          Cadastrar
        </button>
      </form>
    </div>
  );
};

export default Register;
