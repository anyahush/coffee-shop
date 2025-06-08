// src/components/SupplyList.test.jsx
import { render, screen } from '@testing-library/react';
import SupplyList from './SupplyList';

const sample = [
    { id:1, name:'C', category:'Cat', quantityInStock:5, reorderLevel:2, supplier:{ name:'S' } },
    { id:2, name:'D', category:'Dog', quantityInStock:7, reorderLevel:3, supplier:{ name:'T' } },
];

describe('SupplyList', () => {
    test('renders "no supplies" when empty', () => {
        render(<SupplyList items={[]} />);
        expect(screen.getByText('No supplies found.')).toBeInTheDocument();
    });

    test('renders a table row per item', () => {
        render(<SupplyList items={sample} onEdit={()=>{}} onDelete={()=>{}} />);

        // header
        expect(screen.getByText('ID')).toBeInTheDocument();
        // first row values
        expect(screen.getByText('C')).toBeInTheDocument();
        expect(screen.getByText('Cat')).toBeInTheDocument();
        // supplier cell
        expect(screen.getByText('S')).toBeInTheDocument();
        // actions
        expect(screen.getAllByText('Edit')).toHaveLength(2);
        expect(screen.getAllByText('Delete')).toHaveLength(2);
    });
});
