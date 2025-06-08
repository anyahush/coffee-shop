import React from 'react';
import { Link, Outlet } from 'react-router-dom';

export default function Layout() {
    return (
        <div className="d-flex flex-column min-vh-100">
            <header>
                <nav className="navbar navbar-expand-lg" style={{ backgroundColor: '#a5b89f' }}>
                    <div className="container-fluid px-4">
                        <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                            <li className="nav-item">
                                <Link className="nav-link text-white" to="/">Supplies</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link text-white" to="/reorder">Reorder</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link text-white" to="/import">Import</Link>
                            </li>
                        </ul>
                    </div>
                </nav>
            </header>

            <main className="flex-grow-1 py-4 px-4">
                <Outlet />
            </main>

            <footer className="text-center text-muted py-3 bg-light">
                © 2025 Coffee Shop Inventory
            </footer>
        </div>
    );
}
