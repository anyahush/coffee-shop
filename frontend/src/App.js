import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import ReorderPage from './pages/ReorderPage';
import ImportPage from './pages/ImportPage';
import SuppliesPage  from "./pages/SuppliesPage";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Layout />}>
                    <Route index element={<SuppliesPage />} />
                    <Route path="reorder" element={<ReorderPage />} />
                    <Route path="import"  element={<ImportPage />} />
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
