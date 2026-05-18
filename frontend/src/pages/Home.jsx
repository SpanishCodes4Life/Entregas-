const Home = () => {
  return (
    <div>
      <div className="card mb-4 shadow-sm overflow-hidden">
        <div className="row g-0 align-items-center">
          <div className="col-md-6">
            <img
              src="https://images.unsplash.com/photo-1515378791036-0648a3ef77b2?auto=format&fit=crop&w=1200&q=80"
              className="img-fluid rounded-start"
              alt="Entrega de encomenda"
            />
          </div>
          <div className="col-md-6">
            <div className="card-body p-5">
              <h1 className="display-6 fw-bold">Sistema de Entregas</h1>
              <p className="fs-5 text-muted">
                Interface simples em React + Bootstrap. Use a navegação para ver clientes, entregas e trabalhar com o CEP.
              </p>
              <div className="d-grid gap-2 d-md-flex justify-content-md-start">
                <a className="btn btn-primary btn-lg px-4 me-md-2" href="/clientes">
                  Ver Clientes
                </a>
                <a className="btn btn-outline-primary btn-lg px-4" href="/entregas">
                  Ver Entregas
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="row gy-4">
        <div className="col-md-4">
          <div className="card h-100 shadow-sm">
            <img
              src="https://images.unsplash.com/photo-1556740749-887f6717d7e4?auto=format&fit=crop&w=800&q=80"
              className="card-img-top"
              alt="Clientes"
            />
            <div className="card-body">
              <h5 className="card-title">Clientes</h5>
              <p className="card-text">
                Crie e liste clientes. O CEP pode ser consultado e preenchido automaticamente.
              </p>
            </div>
          </div>
        </div>
        <div className="col-md-4">
          <div className="card h-100 shadow-sm">
            <img
              src="https://images.unsplash.com/photo-1519125323398-675f0ddb6308?auto=format&fit=crop&w=800&q=80"
              className="card-img-top"
              alt="Entregas"
            />
            <div className="card-body">
              <h5 className="card-title">Entregas</h5>
              <p className="card-text">
                Liste entregas e veja seus dados principais. Futuramente você pode adicionar filtros por cliente ou status.
              </p>
            </div>
          </div>
        </div>
        <div className="col-md-4">
          <div className="card h-100 shadow-sm">
            <img
              src="https://images.unsplash.com/photo-1494976388531-d1058494cdd8?auto=format&fit=crop&w=800&q=80"
              className="card-img-top"
              alt="CEP"
            />
            <div className="card-body">
              <h5 className="card-title">CEP / ViaCEP</h5>
              <p className="card-text">
                A interface usa a API ViaCEP para preencher endereço automaticamente quando você digitar o CEP.
              </p>
            </div>
          </div>
        </div>
      </div>

      <div className="card mt-4 shadow-sm">
        <div className="card-body">
          <h5 className="card-title">Como abrir a home page</h5>
          <ol>
            <li>Abra o terminal em <code>frontend</code>.</li>
            <li>Execute <code>npm install</code>.</li>
            <li>Execute <code>npm run dev</code>.</li>
            <li>Abra no navegador: <strong>http://localhost:5173</strong></li>
          </ol>
          <p className="mb-0">
            O Spring Boot deve estar rodando em outra porta, por exemplo <strong>http://localhost:8080</strong>.
          </p>
        </div>
      </div>
    </div>
  );
};

export default Home;
