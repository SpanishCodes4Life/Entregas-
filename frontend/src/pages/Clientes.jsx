import { useEffect, useState } from 'react';

const Clientes = () => {
  const [clientes, setClientes] = useState([]);
  const [nome, setNome] = useState('');
  const [cep, setCep] = useState('');
  const [endereco, setEndereco] = useState(null);
  const [mensagem, setMensagem] = useState('');
  const [carregando, setCarregando] = useState(false);

  useEffect(() => {
    fetchClientes();
  }, []);

  const fetchClientes = async () => {
    try {
      const response = await fetch('/api/clientes');
      if (!response.ok) {
        const errorText = await response.text();
        console.error('Fetch clientes erro:', response.status, errorText);
        setMensagem('Erro ao carregar clientes.');
        setClientes([]);
        return;
      }

      const data = await response.json();
      setClientes(Array.isArray(data) ? data : []);
    } catch (error) {
      console.error(error);
      setMensagem('Erro ao carregar clientes.');
      setClientes([]);
    }
  };

  const buscarCep = async () => {
    const cepLimpo = cep.replace(/\D/g, '');
    if (!cepLimpo) {
      setMensagem('Digite um CEP válido.');
      return;
    }

    setCarregando(true);
    setMensagem('Buscando CEP...');

    try {
      const response = await fetch(`https://viacep.com.br/ws/${cepLimpo}/json/`);
      const data = await response.json();

      if (data.erro) {
        setMensagem('CEP não encontrado.');
        setEndereco(null);
      } else {
        setEndereco({
          rua: data.logradouro,
          cidade: data.localidade,
          estado: data.uf,
          cep: data.cep,
        });
        setMensagem('Endereço encontrado.');
      }
    } catch (error) {
      console.error(error);
      setMensagem('Erro ao consultar ViaCEP.');
      setEndereco(null);
    } finally {
      setCarregando(false);
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setMensagem('');

    if (!nome.trim() || !cep.trim()) {
      setMensagem('Preencha nome e CEP antes de salvar.');
      return;
    }

    try {
      const response = await fetch('/api/clientes', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ nome, cep }),
      });

      if (!response.ok) {
        const errorText = await response.text();
        setMensagem(`Erro ao criar cliente: ${errorText}`);
        return;
      }

      const clienteCriado = await response.json();
      setClientes((prev) => [...prev, clienteCriado]);
      setNome('');
      setCep('');
      setEndereco(null);
      setMensagem('Cliente criado com sucesso.');
    } catch (error) {
      console.error(error);
      setMensagem('Falha ao criar cliente.');
    }
  };

  return (
    <div className="row gy-4">
      <div className="col-lg-5">
        <div className="card shadow-sm">
          <div className="card-body">
            <h4 className="card-title mb-3">Cadastrar Cliente</h4>
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label className="form-label">Nome</label>
                <input
                  type="text"
                  className="form-control"
                  value={nome}
                  onChange={(e) => setNome(e.target.value)}
                  placeholder="Nome do cliente"
                />
              </div>
              <div className="mb-3">
                <label className="form-label">CEP</label>
                <div className="input-group">
                  <input
                    type="text"
                    className="form-control"
                    value={cep}
                    onChange={(e) => setCep(e.target.value)}
                    placeholder="00000-000"
                  />
                  <button type="button" className="btn btn-secondary" onClick={buscarCep} disabled={carregando}>
                    {carregando ? 'Buscando...' : 'Buscar CEP'}
                  </button>
                </div>
              </div>
              {endereco && (
                <div className="mb-3 alert alert-success py-3">
                  <div><strong>Rua:</strong> {endereco.rua || '—'}</div>
                  <div><strong>Cidade:</strong> {endereco.cidade || '—'}</div>
                  <div><strong>Estado:</strong> {endereco.estado || '—'}</div>
                </div>
              )}
              <button type="submit" className="btn btn-primary">
                Salvar Cliente
              </button>
            </form>
            {mensagem && <div className="mt-3 alert alert-info">{mensagem}</div>}
          </div>
        </div>
      </div>

      <div className="col-lg-7">
        <div className="card shadow-sm">
          <div className="card-body">
            <h4 className="card-title mb-3">Clientes Cadastrados</h4>
            <div className="table-responsive">
              <table className="table table-hover">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>CEP</th>
                    <th>Rua</th>
                    <th>Cidade</th>
                    <th>Estado</th>
                  </tr>
                </thead>
                <tbody>
                  {clientes.length === 0 ? (
                    <tr>
                      <td colSpan="6" className="text-center text-muted py-4">
                        Nenhum cliente encontrado.
                      </td>
                    </tr>
                  ) : (
                    clientes.map((cliente) => (
                      <tr key={cliente.id}>
                        <td>{cliente.id}</td>
                        <td>{cliente.nome}</td>
                        <td>{cliente.endereco?.cep || '—'}</td>
                        <td>{cliente.endereco?.rua || '—'}</td>
                        <td>{cliente.endereco?.cidade || '—'}</td>
                        <td>{cliente.endereco?.estado || '—'}</td>
                      </tr>
                    ))
                  )}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Clientes;
