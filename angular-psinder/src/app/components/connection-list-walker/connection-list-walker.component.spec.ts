import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConnectionListWalkerComponent } from './connection-list-walker.component';

describe('ConnectionListWalkerComponent', () => {
  let component: ConnectionListWalkerComponent;
  let fixture: ComponentFixture<ConnectionListWalkerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConnectionListWalkerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConnectionListWalkerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
